package au.com.safetychampion.data.domain.usecase.pushnotification

import au.com.safetychampion.data.domain.manager.IFirebaseManager
import au.com.safetychampion.data.util.extension.koinInject

internal class GetFirebaseTokenUseCase {
    private val firebaseManager: IFirebaseManager by koinInject()
    suspend operator fun invoke() = firebaseManager.getFirebaseToken()
}
