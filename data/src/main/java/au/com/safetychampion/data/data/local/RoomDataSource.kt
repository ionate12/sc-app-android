package au.com.safetychampion.data.data.local

import au.com.safetychampion.data.data.common.SyncableRepresentative
import au.com.safetychampion.data.data.local.room.StorableDao
import au.com.safetychampion.data.data.local.room.StorableEntity
import au.com.safetychampion.data.data.local.room.SyncableDao
import au.com.safetychampion.data.data.local.room.SyncableEntity
import au.com.safetychampion.data.domain.manager.IDispatchers
import au.com.safetychampion.data.domain.manager.IGsonManager
import au.com.safetychampion.data.util.extension.koinGet
import au.com.safetychampion.data.util.extension.koinInject
import com.google.gson.JsonObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Calendar

internal class RoomDataSource {
    private val storableDao: StorableDao by koinInject()
    private val syncableDao: SyncableDao by koinInject()
    private val dispatchers: IDispatchers by koinInject()
    private val gson by lazy { koinGet<IGsonManager>().gson }

    /**
     * run async with other tasks
     */
    fun insertStorable(key: String, data: JsonObject) {
        CoroutineScope(dispatchers.io).launch {
            storableDao.insertOrReplace(
                StorableEntity(
                    id = key,
                    data = data
                )
            )
        }
    }

    /**
     * @param expiryAfter: Days
     */
    suspend fun getStorable(id: String, expiryAfter: Int = 5): StorableEntity? {
        try {
            storableDao.findById(id)?.let {
                val fiveDaysAgo =
                    Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -expiryAfter) }.time
                if (it.createdAt.before(fiveDaysAgo)) {
                    return null
                } else {
                    return it
                }
            } ?: return null
        } catch (e: Exception) {
            Timber.e(e)
            return null
        }
    }

    /**
     * await to complete unlike storable
     */
    suspend fun insertSyncable(key: String, data: Any) {
        val json = gson.toJsonTree(data).asJsonObject
        syncableDao.insert(SyncableEntity(key, json, SyncableRepresentative.Status.PENDING))
    }

    suspend fun updateStatusSyncable(key: String, status: SyncableRepresentative.Status) {
        syncableDao.updateStatus(key, status)
    }

    suspend fun getSyncable(key: String): SyncableEntity? {
        return syncableDao.findById(key)
    }

    fun getAllSyncable(): Flow<List<SyncableEntity>> {
        return syncableDao.getAllAsFlow()
    }
}
