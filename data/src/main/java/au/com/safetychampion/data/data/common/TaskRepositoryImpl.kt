package au.com.safetychampion.data.data.common

import au.com.safetychampion.data.data.BaseRepository
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.TaskAssignStatusItem
import au.com.safetychampion.data.domain.models.action.payload.ActiveTask
import au.com.safetychampion.data.domain.models.action.payload.AssignTaskStatusMany
import au.com.safetychampion.data.domain.models.action.payload.AssignTaskStatus
import au.com.safetychampion.data.domain.models.task.Task

class TaskRepositoryImpl(
    private val api: TaskAPI
) : BaseRepository(), TaskRepository {
    override suspend fun getAllActiveTask(body: ActiveTask): Result<List<Task>> {
        return apiCallAsList { api.active(body) }
    }

    override suspend fun assignTaskStatus(body: AssignTaskStatus): Result<List<TaskAssignStatusItem>> {
        return apiCallAsList { api.assignTaskStatus(body) }
    }

    override suspend fun assignTaskStatusMany(body: AssignTaskStatusMany): Result<List<TaskAssignStatusItem>> {
        return apiCallAsList { api.assignTaskStatusMany(body) }
    }

    override suspend fun assignTask(body: AssignTaskStatus): Result<Task> {
        return apiCall { api.assignTask(body) }
    }

    override suspend fun unAssignTask(body: AssignTaskStatus): Result<Task> {
        return apiCall { api.unassignTask(body) }
    }
}
