package au.com.safetychampion.data.domain.usecase.pushnotification

import au.com.safetychampion.data.data.pushnotification.IPushNotificationLocalRepository

class UpdatePushNotificationReadStatusUseCase(
    private val localRepository: IPushNotificationLocalRepository
) {
    suspend fun invoke(
        itemId: String,
        isRead: Boolean
    ) = localRepository.getById(itemId)
        ?.copy(isRead = isRead)
        ?.also { localRepository.save(it) }
}
