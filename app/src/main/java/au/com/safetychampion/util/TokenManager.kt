package au.com.safetychampion.util

import au.com.safetychampion.data.data.local.BaseAppDataStore
import au.com.safetychampion.data.data.local.StoreKey
import au.com.safetychampion.data.domain.core.SuspendableInit
import au.com.safetychampion.data.domain.manager.ITokenManager
import au.com.safetychampion.data.domain.uncategory.AppToken
import au.com.safetychampion.data.util.extension.koinInject
import au.com.safetychampion.dispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.reflect.KClass

class TokenManager : SuspendableInit(), ITokenManager {
    private var tokens: SortedSet<AppToken> = sortedSetOf()
    private val dataStore: BaseAppDataStore by koinInject()

    override suspend fun suspendInit() {
        tokens = getStoredTokens()
    }
    override suspend fun getToken(): AppToken? = didInit(onTimedOut = { null }) {
        tokens.firstOrNull()
    }

    override suspend fun updateToken(token: AppToken) = didInit {
        tokens.removeIf { it.priority == token.priority }
        tokens.add(token)
        storeTokenIfNeeded(token)
    }

    override suspend fun clearTokens() = didInit {
        tokens.clear()
    }

    override suspend fun deleteToken(type: KClass<AppToken>) = didInit {
        tokens.removeIf { it::class == type }
        when (type) {
            AppToken.Morphed::class -> {
                dataStore.store(StoreKey.TokenMorphed, null)
            }
            AppToken.Authed::class -> dataStore.store(StoreKey.TokenAuthed, null)
            else -> Unit
        }
    }

    private suspend fun getStoredTokens(): SortedSet<AppToken> {
        val set: SortedSet<AppToken> = sortedSetOf()
        dataStore.get(StoreKey.TokenAuthed)?.let {
            set.add(AppToken.Authed(it))
        }
        dataStore.get(StoreKey.TokenMorphed)?.let {
            set.add(AppToken.Morphed(it))
        }
        return set
    }

    private fun storeTokenIfNeeded(token: AppToken) {
        CoroutineScope(dispatchers().io).launch {
            when (token) {
                is AppToken.Morphed -> dataStore.store(StoreKey.TokenMorphed, token.value)
                is AppToken.Authed -> dataStore.store(StoreKey.TokenAuthed, token.value)
                else -> Unit
            }
        }
    }
}
