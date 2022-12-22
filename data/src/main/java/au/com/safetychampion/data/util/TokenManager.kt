package au.com.safetychampion.data.util

import au.com.safetychampion.data.domain.uncategory.AppToken
import au.com.safetychampion.data.local.BaseAppDataStore
import au.com.safetychampion.data.local.StoreKey
import au.com.safetychampion.util.koinInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.SortedSet

interface ITokenManager {
    fun getToken(): AppToken?
    fun updateToken(token: AppToken)
}
internal class TokenManager : ITokenManager {
    private lateinit var tokens: SortedSet<AppToken>
    private val dataStore: BaseAppDataStore by koinInject()
    override fun getToken(): AppToken? {
        initTokensIfNeeded()
        return tokens.firstOrNull()
    }

    override fun updateToken(token: AppToken) {
        initTokensIfNeeded()
        tokens.removeIf { it.priority == token.priority }
        tokens.add(token)
        storeTokenIfNeeded(token)
    }

    private fun initTokensIfNeeded() {
        // lazy init
        if (!this::tokens.isInitialized) {
            tokens = runBlocking(dispatchers().io) { getStoredTokens() }
        }
    }

    private suspend fun getStoredTokens(): SortedSet<AppToken> {
        val set: SortedSet<AppToken> = sortedSetOf()
        dataStore.get(StoreKey.StringKey.TokenAuthed)?.let {
            set.add(AppToken.Authed(it))
        }
        dataStore.get(StoreKey.StringKey.TokenMorphed)?.let {
            set.add(AppToken.Morphed(it))
        }
        return set
    }

    private fun storeTokenIfNeeded(token: AppToken) {
        CoroutineScope(dispatchers().io).launch {
            when (token) {
                is AppToken.Morphed -> dataStore.store(StoreKey.StringKey.TokenMorphed, token.value)
                is AppToken.Authed -> dataStore.store(StoreKey.StringKey.TokenAuthed, token.value)
                else -> Unit
            }
        }
    }
}
