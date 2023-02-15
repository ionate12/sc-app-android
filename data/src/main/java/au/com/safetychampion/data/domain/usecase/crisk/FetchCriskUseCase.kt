package au.com.safetychampion.data.domain.usecase.crisk

import au.com.safetychampion.data.data.crisk.ICriskRepository
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.crisk.Crisk

class FetchCriskUseCase(
    private val repository: ICriskRepository
) {
    suspend fun invoke(criskId: String): Result<Crisk> {
        return repository.fetch(criskId)
    }
}
