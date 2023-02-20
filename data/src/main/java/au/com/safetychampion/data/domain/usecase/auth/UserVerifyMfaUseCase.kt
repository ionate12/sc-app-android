package au.com.safetychampion.data.domain.usecase.auth

import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.auth.IAuthRepository
import au.com.safetychampion.data.domain.models.auth.LoginPL
import au.com.safetychampion.data.domain.models.auth.LoginResponse
import au.com.safetychampion.data.domain.models.auth.MfaVerifyPL
import au.com.safetychampion.data.domain.usecase.BaseUseCase
import au.com.safetychampion.data.util.extension.koinInject

class UserVerifyMfaUseCase : BaseUseCase() {
    private val repo: IAuthRepository by koinInject()
    suspend operator fun invoke(mfaVerifyPL: MfaVerifyPL, loginPL: LoginPL): Result<LoginResponse> =
        repo.mfaVerify(mfaVerifyPL, loginPL)
}
