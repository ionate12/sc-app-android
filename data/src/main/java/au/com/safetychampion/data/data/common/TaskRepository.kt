package au.com.safetychampion.data.data.common

import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.TaskAssignStatusItem
import au.com.safetychampion.data.domain.models.task.Task
import au.com.safetychampion.data.domain.payload.ActiveTaskPL
import au.com.safetychampion.data.domain.payload.AssignTaskStatusManyPL
import au.com.safetychampion.data.domain.payload.AssignTaskStatusPL

interface TaskRepository {
    suspend fun getAllActiveTask(body: ActiveTaskPL): Result<List<Task>>
    suspend fun assignTaskStatus(body: AssignTaskStatusPL): Result<List<TaskAssignStatusItem>>
    suspend fun assignTaskStatusMany(body: AssignTaskStatusManyPL): Result<List<TaskAssignStatusItem>>
    suspend fun assignTask(body: AssignTaskStatusPL): Result<Task>
    suspend fun unAssignTask(body: AssignTaskStatusPL): Result<Task>
}
