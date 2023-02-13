package au.com.safetychampion.data.data.local

import au.com.safetychampion.data.data.common.SyncableRepresentative
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.core.SCError
import au.com.safetychampion.data.domain.manager.IGsonManager
import au.com.safetychampion.data.util.extension.koinGet
import au.com.safetychampion.data.util.extension.koinInject
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class SyncableRepository {
    val roomDts: RoomDataSource by koinInject()
    private val gson by lazy { koinGet<IGsonManager>().gson }

    suspend inline fun <reified T> getSyncableData(key: String): Result<T> {
        val type = object : TypeToken<T>() {}.type
        val data: T? = roomDts.getSyncable(key)?.data?.let { gson.fromJson(it, type) }
        return data?.let { Result.Success(it) } ?: Result.Error(SCError.EmptyResult)
    }

    suspend fun hasSyncable(key: String): Boolean {
        return roomDts.getSyncable(key) != null
    }

    fun getSyncableItemFlow(): Flow<List<SyncableRepresentative>> {
        return roomDts.getAllSyncable().map { list -> list.map { it.toRepresentative() } }
    }

    suspend fun insert(key: String, data: Any) {
        roomDts.insertSyncable(key, data)
    }
}
