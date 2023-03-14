package au.com.safetychampion.data.domain.usecase.auth

import au.com.safetychampion.data.data.auth.IAuthRepository
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.auth.mfa.Challenge
import au.com.safetychampion.data.domain.models.auth.mfa.ChallengePL

class ChallengeUserUseCase(
    private val repository: IAuthRepository
) {
    suspend operator fun invoke(payload: ChallengePL): Result<Challenge> {
        return repository.challenge(payload)
    }
}
