package au.com.safetychampion.data.domain.usecase.auth

import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.auth.IAuthRepository
import au.com.safetychampion.data.domain.models.auth.LoginPL
import au.com.safetychampion.data.domain.models.auth.LoginResponse
import au.com.safetychampion.data.domain.models.auth.MultiLoginPL
import au.com.safetychampion.data.domain.usecase.BaseUseCase
import au.com.safetychampion.data.util.extension.koinInject

class UserMultiLoginUseCase : BaseUseCase() {
    private val repo: IAuthRepository by koinInject()
    suspend operator fun invoke(userId: String, loginPL: LoginPL): Result<LoginResponse.Single> =
        repo.multiLogin(MultiLoginPL(MultiLoginPL.User(userId)), loginPL)
}
