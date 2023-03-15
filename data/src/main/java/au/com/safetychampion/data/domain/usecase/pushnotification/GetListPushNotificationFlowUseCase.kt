package au.com.safetychampion.data.domain.usecase.pushnotification

import au.com.safetychampion.data.data.pushnotification.IPushNotificationLocalRepository
import au.com.safetychampion.data.domain.models.pushnotification.PushNotificationEntity
import au.com.safetychampion.data.domain.models.pushnotification.PushNotificationEnv
import au.com.safetychampion.data.domain.models.pushnotification.PushNotificationViewItem
import au.com.safetychampion.data.domain.usecase.BaseUseCase
import au.com.safetychampion.data.util.extension.koinInject
import au.com.safetychampion.data.util.extension.parseObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetListPushNotificationUseCase : BaseUseCase() {
    private val mapper = PushNotificationMapper()
    private val localRepository: IPushNotificationLocalRepository by koinInject()

    suspend fun invoke(userId: String): Flow<List<PushNotificationViewItem>> {
        return localRepository.list(userId)
            .map { list -> list.map { mapper.mapToNotificationViewItem(it) } }
    }
}

class PushNotificationMapper() {
    private fun mapToEnv(input: PushNotificationEntity): PushNotificationEnv? {
        input.data ?: return null
        return input.data.parseObject()
    }

    fun mapToNotificationViewItem(input: PushNotificationEntity) = PushNotificationViewItem(
        _id = input._id,
        title = input.title ?: "",
        body = input.body ?: "",
        env = mapToEnv(input) ?: PushNotificationEnv("", 1, null, null, null),
        isRead = input.isRead,
        dateCreated = input.dateCreated
    )
}
