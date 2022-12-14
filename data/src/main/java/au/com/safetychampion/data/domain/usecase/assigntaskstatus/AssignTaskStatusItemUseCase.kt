package au.com.safetychampion.data.domain.usecase.assigntaskstatus

import au.com.safetychampion.data.data.common.TaskRepository
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.extensions.jsonObjectOf
import au.com.safetychampion.data.domain.models.TaskAssignStatusItem
import au.com.safetychampion.data.domain.models.task.Task
import au.com.safetychampion.data.domain.models.task.TaskPayload

class AssignTaskStatusItemUseCase(
    private val activeTaskRepository: TaskRepository
) {
    suspend operator fun invoke(
        task: Task,
        userId: String? = null,
        notes: String? = null,
        moduleName: String?,
        dateDue: String?
    ): Pair<String, Result<List<TaskAssignStatusItem>>> {
        val body = jsonObjectOf(
            TaskPayload.AssignTask(
                task = task,
                userId = userId,
                notes = notes,
                moduleName = moduleName,
                dateDue = dateDue
            )
        )
        return body.toString() to activeTaskRepository.assignTaskStatus(body!!)
    }
}
