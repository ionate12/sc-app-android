package au.com.safetychampion.data.domain.usecase.pushnotification

import au.com.safetychampion.data.data.pushnotification.IPushNotificationRemoteRepository
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.core.doOnSuccess
import au.com.safetychampion.data.domain.models.pushnotification.FirebaseTokenPL
import timber.log.Timber

class DeleteDeviceTokenUseCase(
    private val repository: IPushNotificationRemoteRepository
) {
    suspend operator fun invoke(fcmToken: String): Result<Any> {
        return repository.delete(FirebaseTokenPL(fcmToken))
            .doOnSuccess {
                Timber.tag("FCMToken").d("Deleted")
            }
    }
}
