package au.com.safetychampion.data.domain.usecase.inspection

import au.com.safetychampion.data.data.inspection.IInspectionRepository
import au.com.safetychampion.data.domain.base.BaseSignOff
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.core.map
import au.com.safetychampion.data.domain.models.inspections.InspectionSignoff
import au.com.safetychampion.data.domain.models.inspections.InspectionTask
import au.com.safetychampion.data.domain.usecase.BasePrepareSignoffUseCase
import au.com.safetychampion.data.util.extension.koinInject

/**
 * This usecase will be used when an inspection is started which has a taskId.
 */
class PrepareSignoffInspectionUseCase : BasePrepareSignoffUseCase<InspectionTask, InspectionSignoff>() {
    private val repo: IInspectionRepository by koinInject()

    override suspend fun fetchData(moduleId: String, taskId: String?): Result<InspectionSignoff> {
        requireNotNull(taskId)
        return repo.fetchTask(moduleId, taskId).map {
            InspectionSignoff(moduleId, it)
        }
    }

    override fun getSyncableKey(moduleId: String, taskId: String?): String {
        requireNotNull(taskId)
        return BaseSignOff.inspectionSignoffSyncableKey(moduleId, taskId)
    }
}
