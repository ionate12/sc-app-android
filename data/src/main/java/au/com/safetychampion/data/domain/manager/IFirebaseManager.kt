package au.com.safetychampion.data.domain.manager
import au.com.safetychampion.data.domain.core.Result

interface IFirebaseManager {
    suspend fun getFirebaseToken(): Result<String>
}
