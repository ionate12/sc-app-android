package au.com.safetychampion.data.domain.usecase.inspection

import au.com.safetychampion.data.data.common.OfflineRequest
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.core.SCError
import au.com.safetychampion.data.domain.core.flatMap
import au.com.safetychampion.data.domain.core.flatMapError
import au.com.safetychampion.data.domain.core.map
import au.com.safetychampion.data.domain.models.inspections.InspectionSignoff
import au.com.safetychampion.data.domain.models.inspections.InspectionTask
import au.com.safetychampion.data.domain.models.inspections.payload.InspectionNewChildPL
import au.com.safetychampion.data.domain.models.inspections.payload.InspectionTaskStartPL
import timber.log.Timber

class NewChildAndStartInspectionUseCase : BaseStartTaskInspectionUseCase() {
    suspend operator fun invoke(
        parentInspId: String,
        date: String,
        notes: String?
    ): Result<InspectionSignoff> {
        val newChild = repo.newChild(
            parentInspId,
            InspectionNewChildPL(date, notes)
        )
        return newChild.flatMap {
            val taskId = it.execute?.task?._id ?: return@flatMap Result.Error(SCError.NoTaskIdFound)
            repo.taskStart(it._id, taskId, InspectionTaskStartPL(null))
        }.flatMap {
            Result.Success(InspectionSignoff(parentInspId, it))
        }.flatMapError { err ->
            when (err) {
                is SCError.NoNetwork -> {
                    simulateTaskForNewChild(
                        parentInspId,
                        date,
                        notes
                    )
                }
                else -> null
            }
        }
    }

    private suspend fun simulateTaskForNewChild(
        parentInspId: String,
        date: String,
        notes: String?
    ): Result<InspectionSignoff> {
        Timber.d("Inspection: New Child offline -> try simulate Task For New Child")
        val offlineRequest = OfflineRequest(
            OfflineRequest.Type.INSP_NEW_CHILD_START,
            InspectionNewChildPL(date, notes)
        )
        return simulateTaskInternal(parentInspId).map {
            InspectionSignoff(
                parentInspId,
                it.copy(
                    dateDue = date,
                    executionMeta = InspectionTask.ExecutionMeta(notes = notes),
                    offlineRequest = offlineRequest
                )
            )
        }
    }
}
