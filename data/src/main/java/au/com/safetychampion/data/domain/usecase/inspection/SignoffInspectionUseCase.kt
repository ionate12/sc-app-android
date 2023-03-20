package au.com.safetychampion.data.domain.usecase.inspection

import au.com.safetychampion.data.data.common.OfflineRequest
import au.com.safetychampion.data.data.inspection.IInspectionRepository
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.core.SCError
import au.com.safetychampion.data.domain.core.dataOrNull
import au.com.safetychampion.data.domain.core.flatMap
import au.com.safetychampion.data.domain.models.inspections.InspectionSignoff
import au.com.safetychampion.data.domain.models.inspections.InspectionTask
import au.com.safetychampion.data.domain.models.inspections.payload.InspectionNewChildPL
import au.com.safetychampion.data.domain.models.inspections.payload.InspectionTaskPL
import au.com.safetychampion.data.domain.models.inspections.payload.InspectionTaskStartPL
import au.com.safetychampion.data.domain.usecase.BaseSignoffUseCase
import au.com.safetychampion.data.util.extension.koinInject

class SignoffInspectionUseCase : BaseSignoffUseCase<InspectionTask, InspectionSignoff>() {

    private val repo: IInspectionRepository by koinInject()
    override suspend fun signoffOnline(payload: InspectionSignoff): Result<InspectionTask> {
        return payload.task.offlineRequest?.let {
            var inspectionId = payload.inspectionId
            val subRequest = when (it.type) {
                OfflineRequest.Type.INSP_NEW_CHILD_START -> {
                    processNewChildAndStart(inspectionId, it.pl as InspectionNewChildPL)
                }
                OfflineRequest.Type.INSP_START -> {
                    repo.taskStart(inspectionId, payload.task._id!!, it.pl as InspectionTaskStartPL)
                }
            }
            val nInspId = subRequest.dataOrNull()?._for?._id ?: return@let Result.Error(SCError.NoTaskIdFound)
            finalSignoff(nInspId, payload.task._id!!, InspectionTaskPL.fromModel(payload.task))
        }
            ?: finalSignoff(payload.inspectionId, payload.task._id!!, InspectionTaskPL.fromModel(payload.task))
    }

    private suspend fun processNewChildAndStart(parentInspId: String, payload: InspectionNewChildPL): Result<InspectionTask> {
        return repo.newChild(parentInspId, payload).flatMap {
            val taskId = it.execute?.task?._id ?: return@flatMap Result.Error(SCError.NoTaskIdFound)
            repo.taskStart(it._id, taskId, InspectionTaskStartPL(null))
        }
    }
    private suspend fun processStart(inspId: String, taskId: String, payload: InspectionTaskStartPL): Result<InspectionTask> {
        return repo.taskStart(inspId, taskId, payload)
    }

    private suspend fun finalSignoff(
        inspId: String,
        taskId: String,
        payload: InspectionTaskPL
    ) = repo.signoff(inspId, taskId, payload)
}
