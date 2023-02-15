package au.com.safetychampion.util

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import au.com.safetychampion.data.domain.manager.IDispatchers
import au.com.safetychampion.data.util.extension.koinInject
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
