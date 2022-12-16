package au.com.safetychampion.data.domain.usecase.activetask

import au.com.safetychampion.data.data.common.TaskRepository
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.task.Task
import au.com.safetychampion.data.domain.payload.ActiveTaskPL

class GetAllActiveTaskUseCase(
    private val activeTaskRepository: TaskRepository
) {
    suspend operator fun invoke(moduleName: String?): Result<List<Task>> {
        val body = ActiveTaskPL(
            moduleName?.let { ActiveTaskPL.Modules(listOf(it)) }
        )
        return activeTaskRepository.getAllActiveTask(body)
    }
}
