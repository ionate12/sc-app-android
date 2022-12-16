package au.com.safetychampion.data.data.common

import au.com.safetychampion.data.data.BaseRepository
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.TaskAssignStatusItem
import au.com.safetychampion.data.domain.models.task.Task
import au.com.safetychampion.data.domain.payload.ActiveTaskPayload
import au.com.safetychampion.data.domain.payload.AssignTaskStatusManyPL
import au.com.safetychampion.data.domain.payload.AssignTaskStatusPL

class TaskRepositoryImpl(
    private val api: TaskAPI
) : BaseRepository(), TaskRepository {
    override suspend fun getAllActiveTask(body: ActiveTaskPayload.ActiveTaskPL): Result<List<Task>> {
        return callAsList { api.active(body) }
    }

    override suspend fun assignTaskStatus(body: AssignTaskStatusPL): Result<List<TaskAssignStatusItem>> {
        return callAsList { api.assignTaskStatus(body) }
    }

    override suspend fun assignTaskStatusMany(body: AssignTaskStatusManyPL): Result<List<TaskAssignStatusItem>> {
        return callAsList { api.assignTaskStatusMany(body) }
    }
}
