package au.com.safetychampion.data.domain.usecase.assigntaskstatus

import au.com.safetychampion.data.data.common.TaskRepository
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.TaskAssignStatusItem
import au.com.safetychampion.data.domain.models.action.AssignTaskStatusPL
import au.com.safetychampion.data.domain.models.task.Task

class AssignTaskStatusItemUseCase(
    private val activeTaskRepository: TaskRepository
) {
    suspend operator fun invoke(
        task: Task,
        userId: String? = null,
        notes: String? = null,
        moduleName: String?,
        dateDue: String?
    ): Result<List<TaskAssignStatusItem>> {
        val body = AssignTaskStatusPL(
            task = task,
            userId = userId,
            notes = notes,
            moduleName = moduleName,
            dateDue = dateDue
        )
        return activeTaskRepository.assignTaskStatus(body)
    }
}
