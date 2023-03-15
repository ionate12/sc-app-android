package au.com.safetychampion.data.domain.usecase.auth

import au.com.safetychampion.data.data.auth.IAuthRepository
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.auth.mfa.ConfirmEnroll
import au.com.safetychampion.data.domain.models.auth.mfa.ConfirmEnrollPL

class ConfirmEnrollAuthenticatorUseCase(
    private val repository: IAuthRepository
) {
    suspend operator fun invoke(payload: ConfirmEnrollPL): Result<ConfirmEnroll> {
        return repository.confirm(payload)
    }
}
