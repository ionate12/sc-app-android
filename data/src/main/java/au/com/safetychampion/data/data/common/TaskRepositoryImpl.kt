package au.com.safetychampion.data.data.common

import au.com.safetychampion.data.data.BaseRepository
import au.com.safetychampion.data.data.api.TaskAPI
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.TaskAssignStatusItem
import au.com.safetychampion.data.domain.models.action.network.ActiveTaskPL
import au.com.safetychampion.data.domain.models.action.network.AssignTaskStatusManyPL
import au.com.safetychampion.data.domain.models.action.network.AssignTaskStatusPL
import au.com.safetychampion.data.domain.models.task.Task

class TaskRepositoryImpl : BaseRepository(), ITaskRepository {
    override suspend fun getAllActiveTask(body: ActiveTaskPL): Result<List<Task>> {
        return TaskAPI.Active(body).callAsList()
    }

    override suspend fun assignTaskStatus(body: AssignTaskStatusPL): Result<List<TaskAssignStatusItem>> {
        return TaskAPI.AssignTaskStatus(body).callAsList()
    }

    override suspend fun assignTaskStatusMany(body: AssignTaskStatusManyPL): Result<List<TaskAssignStatusItem>> {
        return TaskAPI.AssignTaskStatusMany(body).callAsList()
    }

    override suspend fun assignTask(body: AssignTaskStatusPL): Result<Task> {
        return TaskAPI.AssignTask(body).call()
    }

    override suspend fun unAssignTask(body: AssignTaskStatusPL): Result<Task> {
        return TaskAPI.UnAssignTask(body).call()
    }
}
