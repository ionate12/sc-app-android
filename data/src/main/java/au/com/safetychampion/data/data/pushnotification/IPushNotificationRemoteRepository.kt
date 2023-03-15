package au.com.safetychampion.data.data.pushnotification

import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.pushnotification.FirebaseTokenPL

interface IPushNotificationRemoteRepository {
    suspend fun register(payload: FirebaseTokenPL): Result<Any>
    suspend fun fetch(payload: FirebaseTokenPL): Result<Any>
    suspend fun delete(payload: FirebaseTokenPL): Result<Any>
}
