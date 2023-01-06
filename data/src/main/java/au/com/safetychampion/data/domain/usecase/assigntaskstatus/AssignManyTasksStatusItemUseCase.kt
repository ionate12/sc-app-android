package au.com.safetychampion.data.domain.usecase.assigntaskstatus

import au.com.safetychampion.data.data.common.TaskRepository
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.TaskAssignStatusItem
import au.com.safetychampion.data.domain.models.task.Task
import au.com.safetychampion.data.domain.payload.AssignTaskStatusManyPL

class AssignManyTasksStatusItemUseCase(
    private val activeTaskRepository: TaskRepository
) {
    suspend operator fun invoke(
        tasks: List<Task>
    ): Result<List<TaskAssignStatusItem>> {
        val body = AssignTaskStatusManyPL.fromTasks(tasks)
        return activeTaskRepository.assignTaskStatusMany(body)
    }
}
