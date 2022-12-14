package au.com.safetychampion.data.domain.usecase.assigntaskstatus

import au.com.safetychampion.data.data.common.ITaskRepository
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.TaskAssignStatusItem
import au.com.safetychampion.data.domain.models.action.payload.AssignTaskStatusManyPL
import au.com.safetychampion.data.domain.models.task.Task

class AssignManyTasksStatusItemUseCase(
    private val activeTaskRepository: ITaskRepository
) {
    suspend operator fun invoke(
        tasks: List<Task>
    ): Result<List<TaskAssignStatusItem>> {
        val body = AssignTaskStatusManyPL.fromTasks(tasks)
        return activeTaskRepository.assignTaskStatusMany(body)
    }
}
