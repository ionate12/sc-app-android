package au.com.safetychampion.data.domain.usecase.mobileadmin

import au.com.safetychampion.data.data.mobileadmin.IMobileAdminRepository
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.VersionBoard
import au.com.safetychampion.data.domain.usecase.BaseUseCase
import au.com.safetychampion.data.util.extension.koinInject

class GetVersionUseCase : BaseUseCase() {
    private val repository: IMobileAdminRepository by koinInject()

    suspend operator fun invoke(): Result<VersionBoard> {
        return repository.getVersion()
    }
}
