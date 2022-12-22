package au.com.safetychampion.data.util

import au.com.safetychampion.data.local.BaseAppDataStore
import au.com.safetychampion.data.local.StoreKey
import au.com.safetychampion.utils.injectKoin
import java.util.SortedSet

interface ITokenManager {
    suspend fun getToken(): AppToken?
    suspend fun updateToken(token: AppToken)
}
internal class TokenManager : ITokenManager {
    private lateinit var tokens: SortedSet<AppToken>
    private val dataStore: BaseAppDataStore by injectKoin()
    override suspend fun getToken(): AppToken? {
        // lazy init
        if (!this::tokens.isInitialized) {
            tokens = getStoredTokens()
        }
        return tokens.firstOrNull()
    }

    override suspend fun updateToken(token: AppToken) {
        tokens.removeIf { it.priority == token.priority }
        tokens.add(token)
        storeTokenIfNeeded(token)
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

    private suspend fun storeTokenIfNeeded(token: AppToken) {
        when (token) {
            is AppToken.Morphed -> dataStore.store(StoreKey.StringKey.TokenMorphed, token.value)
            is AppToken.Authed -> dataStore.store(StoreKey.StringKey.TokenAuthed, token.value)
            else -> Unit
        }
    }
}

sealed class AppToken(open val value: String, val priority: Int) : Comparable<AppToken> {
    // For Login process
    data class MFA(override val value: String, val oob: String?) : AppToken(value, 0)
    data class MultiUser(override val value: String) : AppToken(value, 1)

    // For Logged user usage
    data class Morphed(override val value: String) : AppToken(value, 2)
    data class Authed(override val value: String) : AppToken(value, 3)

    override fun compareTo(other: AppToken): Int {
        val firstCheck = priority - other.priority
        return when (firstCheck == 0) {
            true -> value.compareTo(other.value)
            false -> firstCheck
        }
    }
}
