package au.com.safetychampion.data.domain.usecase.assigntaskstatus

import au.com.safetychampion.data.data.common.TaskRepository
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.extensions.jsonArrayOf
import au.com.safetychampion.data.domain.extensions.jsonObjectOf
import au.com.safetychampion.data.domain.models.TaskAssignStatusItem
import au.com.safetychampion.data.domain.models.task.Task
import au.com.safetychampion.data.domain.models.task.TaskPayload

class AssignManyTasksStatusItemUseCase(
    private val activeTaskRepository: TaskRepository
) {
    suspend operator fun invoke(
        tasks: List<Task>
    ): Pair<String, Result<List<TaskAssignStatusItem>>> {
        val mappedTask = tasks.map(TaskPayload::AssignTask)
        val body = jsonObjectOf(
            "_ids" to jsonArrayOf(mappedTask)
        )
        return body.toString() to (activeTaskRepository.assignTaskStatus(body))
    }
}
