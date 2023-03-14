package au.com.safetychampion.data.data.pushnotification

import au.com.safetychampion.data.domain.models.pushnotification.PushNotificationEntity
import kotlinx.coroutines.flow.Flow

interface IPushNotificationLocalRepository {
    suspend fun save(item: PushNotificationEntity)
    suspend fun getById(id: String): PushNotificationEntity?
    fun list(userId: String): Flow<List<PushNotificationEntity>>
    suspend fun delete(itemId: String)
}
