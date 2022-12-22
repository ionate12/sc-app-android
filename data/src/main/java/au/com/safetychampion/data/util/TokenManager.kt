package au.com.safetychampion.data.util

import au.com.safetychampion.data.domain.uncategory.AppToken
import au.com.safetychampion.data.local.BaseAppDataStore
import au.com.safetychampion.data.local.StoreKey
import au.com.safetychampion.util.koinInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.SortedSet

interface ITokenManager {
    suspend fun getToken(): AppToken?
    suspend fun updateToken(token: AppToken)
}
internal class TokenManager : ITokenManager {
    private lateinit var tokens: SortedSet<AppToken>
    private val dataStore: BaseAppDataStore by koinInject()
    override suspend fun getToken(): AppToken? {
        initTokensIfNeeded()
        return tokens.firstOrNull()
    }

    override suspend fun updateToken(token: AppToken) {
        initTokensIfNeeded()
        tokens.removeIf { it.priority == token.priority }
        tokens.add(token)
        storeTokenIfNeeded(token)
    }

    private suspend fun initTokensIfNeeded() {
        // lazy init
        if (!this::tokens.isInitialized) {
            tokens = getStoredTokens()
        }
    }

    private suspend fun getStoredTokens(): SortedSet<AppToken> {
        val set: SortedSet<AppToken> = sortedSetOf()
        dataStore.get(StoreKey.AsString.TokenAuthed)?.let {
            set.add(AppToken.Authed(it))
        }
        dataStore.get(StoreKey.AsString.TokenMorphed)?.let {
            set.add(AppToken.Morphed(it))
        }
        return set
    }

    private fun storeTokenIfNeeded(token: AppToken) {
        CoroutineScope(dispatchers().io).launch {
            when (token) {
                is AppToken.Morphed -> dataStore.store(StoreKey.AsString.TokenMorphed, token.value)
                is AppToken.Authed -> dataStore.store(StoreKey.AsString.TokenAuthed, token.value)
                else -> Unit
            }
        }
    }
}
