package au.com.safetychampion.data.data.pushnotification

import au.com.safetychampion.data.domain.models.pushnotification.PushNotificationEntity
import au.com.safetychampion.data.util.extension.koinInject
import kotlinx.coroutines.flow.Flow

class PushNotificationLocalRepository : IPushNotificationLocalRepository {
    private val dao: PushNotificationDAO by koinInject()

    override suspend fun save(item: PushNotificationEntity) {
        dao.insertOrUpdate(item)
    }

    override suspend fun getById(id: String): PushNotificationEntity {
        return dao.getById(id)
    }

    override fun list(userId: String): Flow<List<PushNotificationEntity>> {
        return dao.getAll(userId)
    }

    override suspend fun delete(itemId: String) {
        dao.delete(itemId)
    }
}
