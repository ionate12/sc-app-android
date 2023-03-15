package au.com.safetychampion.data.domain.usecase.pushnotification

import au.com.safetychampion.data.data.pushnotification.IPushNotificationRemoteRepository
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.core.flatMapError
import au.com.safetychampion.data.domain.core.map
import au.com.safetychampion.data.domain.models.pushnotification.FirebaseTokenPL

internal class RegisterOrFetchFirebaseTokenUseCase(
    private val repository: IPushNotificationRemoteRepository
) {
    /** @return [Result.Success] if the given firebase token can be fetch or register; */
    suspend fun invoke(fireBaseToken: String): Result<String> {
        return repository.fetch(payload = FirebaseTokenPL(fireBaseToken)) // 1. Try to fetch
            .flatMapError { repository.register(payload = FirebaseTokenPL(fireBaseToken)) } // If fetch failed, try to register
            // 2. Return fireBaseToken on Result.Success
            .map { fireBaseToken }
    }
}
