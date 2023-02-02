package au.com.safetychampion.data.domain.usecase.crisk

import au.com.safetychampion.data.data.crisk.ICriskRepository
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.crisk.CriskSignoff
import au.com.safetychampion.data.domain.usecase.BasePrepareSignoffUseCase

class GetCriskSignoffDetailsUseCase(
    private val repository: ICriskRepository
) : BasePrepareSignoffUseCase<CriskSignoff>() {
    suspend operator fun invoke(taskId: String, criskId: String): Result<CriskSignoff> {
        return fromOfflineTaskFirst(
            offlineTaskId = taskId,
            remote = { repository.combineFetchAndTask(taskId, criskId) }
        )
    }
}
