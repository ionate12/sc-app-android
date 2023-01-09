package au.com.safetychampion.data.data.common

import au.com.safetychampion.data.data.BaseRepository
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.TaskAssignStatusItem
import au.com.safetychampion.data.domain.models.action.ActiveTaskPL
import au.com.safetychampion.data.domain.models.action.AssignTaskStatusManyPL
import au.com.safetychampion.data.domain.models.action.AssignTaskStatusPL
import au.com.safetychampion.data.domain.models.task.Task

class TaskRepositoryImpl(
    private val api: TaskAPI
) : BaseRepository(), TaskRepository {
    override suspend fun getAllActiveTask(body: ActiveTaskPL): Result<List<Task>> {
        return apiCallAsList { api.active(body) }
    }

    override suspend fun assignTaskStatus(body: AssignTaskStatusPL): Result<List<TaskAssignStatusItem>> {
        return apiCallAsList { api.assignTaskStatus(body) }
    }

    override suspend fun assignTaskStatusMany(body: AssignTaskStatusManyPL): Result<List<TaskAssignStatusItem>> {
        return apiCallAsList { api.assignTaskStatusMany(body) }
    }

    override suspend fun assignTask(body: AssignTaskStatusPL): Result<Task> {
        return apiCall { api.assignTask(body) }
    }

    override suspend fun unAssignTask(body: AssignTaskStatusPL): Result<Task> {
        return apiCall { api.unassignTask(body) }
    }
}
