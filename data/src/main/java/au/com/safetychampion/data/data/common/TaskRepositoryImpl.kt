package au.com.safetychampion.data.data.common

import au.com.safetychampion.data.data.BaseRepository
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.TaskAssignStatusItem
import au.com.safetychampion.data.domain.models.task.Task
import au.com.safetychampion.data.domain.payload.AssignTaskStatusManyPL
import au.com.safetychampion.data.domain.payload.AssignTaskStatusPL
import com.google.gson.JsonObject

class TaskRepositoryImpl(
    private val api: TaskAPI
) : BaseRepository(), TaskRepository, TaskDAO {
    override suspend fun getAllActiveTask(body: JsonObject): Result<List<Task>> {
        return callAsList(
            call = { api.active(body) },
            tClass = Task::class.java
        )
    }

    override suspend fun assignTaskStatus(body: JsonObject): Result<List<TaskAssignStatusItem>> {
        return callAsList(
            call = { api.assignTaskStatus(body) },
            tClass = TaskAssignStatusItem::class.java
        )
    }

    override suspend fun assignTaskStatus2(body: AssignTaskStatusPL): Result<List<TaskAssignStatusItem>> {
        return api.assignTaskStatus(body).toItems()
    }

    override suspend fun assignTaskStatusMany(body: AssignTaskStatusManyPL): Result<List<TaskAssignStatusItem>> {
        return api.assignTaskStatusMany(body).toItems()
    }

    override fun insert(vararg tasks: Task?) {
        println("Inserted Tasks Not Implemented: ${tasks.size}")
    }
}
