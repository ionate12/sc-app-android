package au.com.safetychampion.data.domain.usecase.auth

import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.WhoAmI
import au.com.safetychampion.data.domain.models.auth.IAuthRepository
import au.com.safetychampion.data.domain.usecase.BaseUseCase
import au.com.safetychampion.data.util.extension.koinInject

class GetWhoAmIUseCase : BaseUseCase() {
    private val repo: IAuthRepository by koinInject()
    suspend operator fun invoke(): Result<WhoAmI> = repo.whoami()
}
