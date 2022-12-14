package au.com.safetychampion.data.domain.usecase.activetask

import au.com.safetychampion.data.data.common.TaskRepository
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.extensions.jsonObjectOf
import au.com.safetychampion.data.domain.models.task.Task
import com.google.gson.JsonObject

class GetAllActiveTaskUseCase(
    private val activeTaskRepository: TaskRepository
) {
    suspend operator fun invoke(moduleName: String?): Result<List<Task>> {
        val body = if (moduleName == null) {
            JsonObject()
        } else {
            jsonObjectOf("{\"filter\":{\"modules\":[\"${moduleName}\"]}}")!!
        }
        return activeTaskRepository.getAllActiveTask(body)
    }
}
