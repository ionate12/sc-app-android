package au.com.safetychampion.data.domain.usecase.auth

import au.com.safetychampion.data.data.auth.IAuthRepository
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.auth.LoginResponse
import au.com.safetychampion.data.domain.models.auth.MorphPL
import au.com.safetychampion.data.domain.usecase.BaseUseCase
import au.com.safetychampion.data.util.extension.koinInject

class UserMorphUseCase : BaseUseCase() {
    private val repo: IAuthRepository by koinInject()
    suspend operator fun invoke(morphId: String): Result<LoginResponse.Single> =
        repo.morph(MorphPL.create(morphId))
}
