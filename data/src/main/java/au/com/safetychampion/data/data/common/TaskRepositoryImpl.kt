package au.com.safetychampion.data.data.common

import au.com.safetychampion.data.data.BaseRepository
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.TaskAssignStatusItem
import au.com.safetychampion.data.domain.models.task.Task
import au.com.safetychampion.data.domain.payload.ActiveTaskPL
import au.com.safetychampion.data.domain.payload.AssignTaskStatusManyPL
import au.com.safetychampion.data.domain.payload.AssignTaskStatusPL

class TaskRepositoryImpl(
    private val api: TaskAPI
) : BaseRepository(), TaskRepository {
    override suspend fun getAllActiveTask(body: ActiveTaskPL): Result<List<Task>> {
        return callAsList { api.active(body) }
    }

    override suspend fun assignTaskStatus(body: AssignTaskStatusPL): Result<List<TaskAssignStatusItem>> {
        return callAsList { api.assignTaskStatus(body) }
    }

    override suspend fun assignTaskStatusMany(body: AssignTaskStatusManyPL): Result<List<TaskAssignStatusItem>> {
        return callAsList { api.assignTaskStatusMany(body) }
    }

    override suspend fun assignTask(body: AssignTaskStatusPL): Result<Task> {
        return call { api.assignTask(body) }
    }

    override suspend fun unAssignTask(body: AssignTaskStatusPL): Result<Task> {
        return call { api.unassignTask(body) }
    }
}
