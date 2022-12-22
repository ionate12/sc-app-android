package au.com.safetychampion.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import au.com.safetychampion.data.util.IDispatchers
import au.com.safetychampion.util.koinInject
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

abstract class BaseAppDataStore {
    protected abstract val store: DataStore<Preferences>
    private val dispatchers: IDispatchers by koinInject()

    suspend fun <T : Any> store(storeKey: StoreKey<T>, value: T?) {
        store.edit {
            if (value == null) {
                it.remove(storeKey.prefKey())
                return@edit
            }
            it[storeKey.prefKey()] = value
        }
    }

    suspend fun <T : Any> get(storeKey: StoreKey<T>): T? {
        return store.data.map {
            it[storeKey.prefKey()]
        }.firstOrNull()
    }

    fun <T : Any> getImmediate(storeKey: StoreKey<T>): T? = runBlocking(dispatchers.io) {
        get(storeKey)
    }
}

sealed class StoreKey<T : Any>(protected open val key: String) {
    abstract fun prefKey(): Preferences.Key<T>
    sealed class AsString(override val key: String) : StoreKey<String>(key) {
        override fun prefKey(): Preferences.Key<String> = stringPreferencesKey(key)

        object TokenAuthed : AsString("token_authed")
        object TokenMorphed : AsString("token_morphed")
    }

    sealed class AsBoolean(override val key: String) : StoreKey<Boolean>(key) {
        override fun prefKey(): Preferences.Key<Boolean> = booleanPreferencesKey(key)
        // To add more boolean value
    }
    sealed class AsInt(override val key: String) : StoreKey<Int>(key) {
        override fun prefKey(): Preferences.Key<Int> = intPreferencesKey(key)
        // To add more Int Value
    }
    sealed class AsStringSet(override val key: String) : StoreKey<Set<String>>(key) {
        override fun prefKey(): Preferences.Key<Set<String>> = stringSetPreferencesKey(key)
        // To add more String Set Value
    }
}
