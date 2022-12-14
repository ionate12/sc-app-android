package au.com.safetychampion.data.data.common

import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.TaskAssignStatusItem
import au.com.safetychampion.data.domain.models.task.Task
import au.com.safetychampion.data.domain.payload.AssignTaskStatusManyPL
import au.com.safetychampion.data.domain.payload.AssignTaskStatusPL
import com.google.gson.JsonObject

interface TaskRepository {
    suspend fun getAllActiveTask(body: JsonObject): Result<List<Task>>
    suspend fun assignTaskStatus(body: JsonObject): Result<List<TaskAssignStatusItem>>
    suspend fun assignTaskStatus2(body: AssignTaskStatusPL): Result<List<TaskAssignStatusItem>>
    suspend fun assignTaskStatusMany(body: AssignTaskStatusManyPL): Result<List<TaskAssignStatusItem>>
}
