package au.com.safetychampion.data.domain.usecase.auth

import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.auth.IAuthRepository
import au.com.safetychampion.data.domain.models.auth.LoginResponse
import au.com.safetychampion.data.domain.usecase.BaseUseCase
import au.com.safetychampion.data.util.extension.koinInject

class UserUnMorphUseCase : BaseUseCase() {
    private val repo: IAuthRepository by koinInject()
    suspend operator fun invoke(): Result<LoginResponse.Single> = repo.unmorph()
}
