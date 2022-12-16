package au.com.safetychampion.data.domain.usecase.activetask

import au.com.safetychampion.data.data.common.TaskRepository
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.task.Task
import au.com.safetychampion.data.domain.payload.ActiveTaskPayload

class GetAllActiveTaskUseCase(
    private val activeTaskRepository: TaskRepository
) {
    suspend operator fun invoke(moduleName: String?): Result<List<Task>> {
        val body = ActiveTaskPayload.ActiveTaskPL.fromModuleName(listOf(moduleName!!))
        return activeTaskRepository.getAllActiveTask(body)
    }
}
