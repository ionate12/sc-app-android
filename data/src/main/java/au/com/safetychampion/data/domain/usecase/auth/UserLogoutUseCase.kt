package au.com.safetychampion.data.domain.usecase.auth

import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.auth.IAuthRepository
import au.com.safetychampion.data.domain.usecase.BaseUseCase
import au.com.safetychampion.data.util.extension.koinInject

class UserLogoutUseCase : BaseUseCase() {
    private val repo by koinInject<IAuthRepository>()
    suspend operator fun invoke(): Result<Unit> {
        return repo.logout()
        // TODO: Clear all existing bg tasks/services
    }
}
