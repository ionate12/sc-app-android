package au.com.safetychampion.data.domain.usecase.pushnotification

import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.core.doOnFailure
import au.com.safetychampion.data.domain.core.doOnSuccess
import au.com.safetychampion.data.domain.core.flatMap
import au.com.safetychampion.data.util.extension.koinInject
import timber.log.Timber

class SetupPushNotificationUseCase {
    private val getFirebaseTokenUseCase: GetFirebaseTokenUseCase by koinInject()
    private val registerOrFetchDeviceToken: RegisterOrFetchFirebaseTokenUseCase by koinInject()

    suspend fun invoke(): Result<String> {
        return getFirebaseTokenUseCase.invoke()
            .flatMap { registerOrFetchDeviceToken.invoke(it) }
            .doOnSuccess { token ->
                // TODO("save token to sharedpref")
                Timber.tag("FCMToken").d("SetupPushNotification success")
            }.doOnFailure {
                Timber.tag("FCMToken").d("SetupPushNotification failed")
            }
    }
}
