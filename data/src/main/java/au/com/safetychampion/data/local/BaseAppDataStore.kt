package au.com.safetychampion.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import au.com.safetychampion.data.util.IDispatchers
import au.com.safetychampion.util.koinInject
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

abstract class BaseAppDataStore {
    protected abstract val store: DataStore<Preferences>
    private val dispatchers: IDispatchers by koinInject()

    @Suppress("UNCHECKED_CAST")
    suspend fun <T : Any> store(storeKey: StoreKey<T>, value: T?) {
        store.edit {
            if (value == null) {
                it.remove(storeKey.key)
                return@edit
            }
            when (storeKey) {
                is StoreKey.StringKey -> {
                    it[storeKey.key] = value as String
                }
                is StoreKey.BooleanKey -> {
                    it[storeKey.key] = value as Boolean
                }
                is StoreKey.IntKey -> {
                    it[storeKey.key] = value as Int
                }
                is StoreKey.StringSetKey -> {
                    it[storeKey.key] = value as Set<String>
                }
            }
        }
    }

    suspend fun <T : Any> get(storeKey: StoreKey<T>): T? {
        return store.data.map {
            it[storeKey.key]
        }.firstOrNull()
    }

    fun <T : Any> getImmediate(storeKey: StoreKey<T>): T? = runBlocking(dispatchers.io) {
        get(storeKey)
    }
}

sealed class StoreKey<T : Any>(open val key: Preferences.Key<T>) {
    sealed class StringKey(override val key: Preferences.Key<String>) : StoreKey<String>(key) {
        object TokenAuthed : StringKey(stringPreferencesKey("token_authed"))
        object TokenMorphed : StringKey(stringPreferencesKey("token_morphed"))
    }
    sealed class BooleanKey(override val key: Preferences.Key<Boolean>) : StoreKey<Boolean>(key) {
        // To add more boolean value
    }
    sealed class IntKey(override val key: Preferences.Key<Int>) : StoreKey<Int>(key) {
        // To add more Int Value
    }
    sealed class StringSetKey(override val key: Preferences.Key<Set<String>>) : StoreKey<Set<String>>(key) {
        // To add more String Set Value
    }
}
