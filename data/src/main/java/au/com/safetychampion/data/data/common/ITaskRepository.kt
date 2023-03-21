package au.com.safetychampion.data.data.common

import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.action.network.ActiveTaskPL
import au.com.safetychampion.data.domain.models.action.network.AssignTaskStatusManyPL
import au.com.safetychampion.data.domain.models.action.network.AssignTaskStatusPL
import au.com.safetychampion.data.domain.models.task.Task
import au.com.safetychampion.data.domain.models.task.TaskAssignStatusItem

interface ITaskRepository {
    suspend fun getAllActiveTask(body: ActiveTaskPL): Result<List<Task>>
    suspend fun assignTaskStatus(body: AssignTaskStatusPL): Result<List<TaskAssignStatusItem>>
    suspend fun assignTaskStatusMany(body: AssignTaskStatusManyPL): Result<List<TaskAssignStatusItem>>
    suspend fun assignTask(body: AssignTaskStatusPL): Result<Task>
    suspend fun unAssignTask(body: AssignTaskStatusPL): Result<Task>
}
