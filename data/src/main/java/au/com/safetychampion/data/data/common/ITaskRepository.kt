package au.com.safetychampion.data.data.common

import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.TaskAssignStatusItem
import au.com.safetychampion.data.domain.models.action.payload.ActiveTaskPL
import au.com.safetychampion.data.domain.models.action.payload.AssignTaskStatus
import au.com.safetychampion.data.domain.models.action.payload.AssignTaskStatusManyPL
import au.com.safetychampion.data.domain.models.task.Task

interface ITaskRepository {
    suspend fun getAllActiveTask(body: ActiveTaskPL): Result<List<Task>>
    suspend fun assignTaskStatus(body: AssignTaskStatus): Result<List<TaskAssignStatusItem>>
    suspend fun assignTaskStatusMany(body: AssignTaskStatusManyPL): Result<List<TaskAssignStatusItem>>
    suspend fun assignTask(body: AssignTaskStatus): Result<Task>
    suspend fun unAssignTask(body: AssignTaskStatus): Result<Task>
}
