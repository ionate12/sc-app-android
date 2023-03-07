package au.com.safetychampion.data.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import au.com.safetychampion.data.domain.manager.IDispatchers
import au.com.safetychampion.data.domain.manager.IGsonManager
import au.com.safetychampion.data.util.extension.koinGet
import au.com.safetychampion.data.util.extension.koinInject
import au.com.safetychampion.data.util.extension.parseObject
import com.google.gson.Gson
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

abstract class BaseAppDataStore {
    abstract val store: DataStore<Preferences>
    private val dispatchers: IDispatchers by koinInject()
    private val gson: Gson by lazy { koinGet<IGsonManager>().gson }

    suspend fun <T : Any> store(storeKey: StoreKey<T>, value: T?) {
        store.edit {
            if (value == null) {
                it.remove(storeKey.prefKey())
                return@edit
            }
            it[storeKey.prefKey()] = value
        }
    }
    suspend fun store(storeKey: StoreKey.AsObject, value: Any?) {
        store.edit {
            if (value == null) {
                it.remove(storeKey.prefKey())
                return@edit
            }
            it[storeKey.prefKey()] = gson.toJson(value)
        }
    }

    suspend fun <T : Any> get(storeKey: StoreKey<T>): T? {
        return store.data.map {
            it[storeKey.prefKey()]
        }.firstOrNull()
    }

    suspend inline fun <reified T : Any> get(storeKey: StoreKey.AsObject): T? {
        return store.data.map {
            it[storeKey.prefKey()]
        }.firstOrNull()?.parseObject()
    }

    fun <T : Any> getImmediate(storeKey: StoreKey<T>): T? = runBlocking(dispatchers.io) {
        get(storeKey)
    }
}
