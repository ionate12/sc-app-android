package au.com.safetychampion.data.domain.usecase.crisk

import au.com.safetychampion.data.data.crisk.ICriskRepository
import au.com.safetychampion.data.domain.base.BaseSignOff
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.crisk.CriskSignoff
import au.com.safetychampion.data.domain.models.crisk.CriskTask
import au.com.safetychampion.data.domain.usecase.BasePrepareSignoffUseCase
import au.com.safetychampion.data.util.extension.koinInject

class PrepareSignoffCriskUseCase : BasePrepareSignoffUseCase<CriskTask, CriskSignoff>() {

    private val repository: ICriskRepository by koinInject()

    override suspend fun fetchData(moduleId: String, taskId: String?): Result<CriskSignoff> {
        requireNotNull(taskId)
        return repository.combineFetchAndTask(criskId = moduleId, taskId = taskId)
    }

    override fun getSyncableKey(moduleId: String, taskId: String?): String {
        requireNotNull(taskId)
        return BaseSignOff.criskSignoffSyncableKey(moduleId, taskId)
    }
}
