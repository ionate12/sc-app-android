package au.com.safetychampion.data.data.common

import au.com.safetychampion.data.data.BaseRepository
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.Task
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

    override fun insert(vararg tasks: Task?) {
        println("Inserted Tasks Not Implemented: ${tasks.size}")
    }
}
