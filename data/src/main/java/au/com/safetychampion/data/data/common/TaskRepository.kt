package au.com.safetychampion.data.data.common

import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.Task
import com.google.gson.JsonObject

interface TaskRepository {
    suspend fun getAllActiveTask(body: JsonObject): Result<List<Task>>
}
