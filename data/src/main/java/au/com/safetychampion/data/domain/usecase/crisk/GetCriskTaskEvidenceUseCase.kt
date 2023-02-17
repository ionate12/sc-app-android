package au.com.safetychampion.data.domain.usecase.crisk

import au.com.safetychampion.data.data.crisk.ICriskRepository
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.UpdateLogListItem

class GetCriskTaskEvidenceUseCase(
    private val repository: ICriskRepository
) {
    suspend fun invoke(criskId: String): Result<List<UpdateLogListItem>> {
        return repository.evidences(criskId)
    }
}
