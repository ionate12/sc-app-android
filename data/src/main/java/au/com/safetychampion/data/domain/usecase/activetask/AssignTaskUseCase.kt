package au.com.safetychampion.data.domain.usecase.activetask

import au.com.safetychampion.data.data.common.TaskRepository
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.action.AssignTaskStatusPL
import au.com.safetychampion.data.domain.models.task.Task

class AssignTaskUseCase(
    private val activeTaskRepository: TaskRepository
) {
    suspend operator fun invoke(
        task: Task,
        toUserId: String,
        notes: String?,
        moduleName: String?,
        dateDue: String?
    ): Result<Task> {
        val body = AssignTaskStatusPL(
            task = task,
            userId = toUserId,
            notes = notes,
            moduleName = moduleName,
            dateDue = dateDue
        )
        return activeTaskRepository.assignTask(body)
    }
}
