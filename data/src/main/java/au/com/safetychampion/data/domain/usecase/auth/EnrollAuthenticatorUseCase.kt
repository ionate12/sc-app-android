package au.com.safetychampion.data.domain.usecase.auth

import au.com.safetychampion.data.data.auth.IAuthRepository
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.auth.mfa.Enroll
import au.com.safetychampion.data.domain.models.auth.mfa.EnrollPL

class EnrollAuthenticatorUseCase(
    private val repository: IAuthRepository
) {
    suspend operator fun invoke(payload: EnrollPL): Result<Enroll> {
        return repository.enroll(payload)
    }
}
