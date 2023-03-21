package au.com.safetychampion.data.domain.usecase.mobileadmin

import au.com.safetychampion.data.data.mobileadmin.IMobileAdminRepository
import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.mobileadmin.VersionBoard

class VersionPL(
    val versionName: String? = null,
    val platform: Platform = Platform()
) : BasePL() {
    class Platform(
        val name: String = "ANDROID"
    )
}

class GetVersionUseCase(
    private val repository: IMobileAdminRepository
) {
    suspend fun invoke(versionName: String?): Result<VersionBoard> {
        return repository.getVersion(
            payload = VersionPL(versionName)
        )
    }
}
