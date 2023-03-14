package au.com.safetychampion.data.data.pushnotification

import au.com.safetychampion.data.data.BaseRepository
import au.com.safetychampion.data.data.api.PushNotificationAPI
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.pushnotification.FirebaseTokenPL

class PushNotificationRemoteRepository : BaseRepository(), IPushNotificationRemoteRepository {
    override suspend fun register(payload: FirebaseTokenPL): Result<Any> {
        return PushNotificationAPI.Register(payload).call()
    }

    override suspend fun fetch(payload: FirebaseTokenPL): Result<Any> {
        return PushNotificationAPI.Fetch(payload).call()
    }

    override suspend fun delete(payload: FirebaseTokenPL): Result<Any> {
        return PushNotificationAPI.Delete(payload).call()
    }
}
