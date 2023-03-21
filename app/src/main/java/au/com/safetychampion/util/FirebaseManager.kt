package au.com.safetychampion.util

import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.manager.IFirebaseManager

class FirebaseManager : IFirebaseManager {
    override suspend fun getFirebaseToken(): Result<String> {
        TODO()
    }
}
