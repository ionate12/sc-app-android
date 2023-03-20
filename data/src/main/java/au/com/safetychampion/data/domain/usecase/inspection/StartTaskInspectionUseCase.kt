package au.com.safetychampion.data.domain.usecase.inspection

import au.com.safetychampion.data.data.common.OfflineRequest
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.core.SCError
import au.com.safetychampion.data.domain.core.flatMapError
import au.com.safetychampion.data.domain.core.map
import au.com.safetychampion.data.domain.models.inspections.InspectionSignoff
import au.com.safetychampion.data.domain.models.inspections.payload.InspectionTaskStartPL

class StartTaskInspectionUseCase : BaseStartTaskInspectionUseCase() {
    suspend operator fun invoke(
        inspectionId: String,
        taskId: String,
        date: String
    ): Result<InspectionSignoff> = repo.taskStart(
        inspectionId,
        taskId,
        InspectionTaskStartPL(date)
    ).map {
        InspectionSignoff(inspectionId, it)
    }.flatMapError {
        if (it is SCError.NoNetwork) {
            simulateForStartTask(inspectionId, taskId, date)
        } else { null }
    }

    private suspend fun simulateForStartTask(
        inspId: String,
        taskId: String,
        date: String
    ): Result<InspectionSignoff> {
        val offlineRequest = OfflineRequest(OfflineRequest.Type.INSP_START, InspectionTaskStartPL(date))
        return simulateTaskInternal(inspId).map {
            InspectionSignoff(
                inspId,
                it.copy(_id = taskId, dateCommenced = date, offlineRequest = offlineRequest)
            )
        }
    }
}
