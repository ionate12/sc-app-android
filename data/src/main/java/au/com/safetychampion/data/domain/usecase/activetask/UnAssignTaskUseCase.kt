package au.com.safetychampion.data.domain.usecase.activetask

import au.com.safetychampion.data.data.common.ITaskRepository
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.action.payload.AssignTaskStatus
import au.com.safetychampion.data.domain.models.task.Task

class UnAssignTaskUseCase(
    private val activeTaskRepository: ITaskRepository
) {
    suspend operator fun invoke(
        task: Task,
        toUserId: String,
        notes: String?,
        moduleName: String?,
        dateDue: String
    ): Result<Task> {
        val body = AssignTaskStatus(
            task = task,
            userId = toUserId,
            notes = notes,
            moduleName = moduleName,
            dateDue = dateDue
        )
        return activeTaskRepository.unAssignTask(body)
    }
}
