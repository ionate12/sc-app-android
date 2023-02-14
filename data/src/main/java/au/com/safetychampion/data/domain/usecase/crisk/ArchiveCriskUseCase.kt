package au.com.safetychampion.data.domain.usecase.crisk

import au.com.safetychampion.data.data.crisk.ICriskRepository
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.crisk.CriskArchivePayload
import com.google.gson.JsonObject

class ArchiveCriskUseCase(
    private val repository: ICriskRepository
) {
    suspend operator fun invoke(criskId: String, payload: CriskArchivePayload): Result<JsonObject> {
        return repository.archive(
            criskId,
            payload
        )
    }
}
