package au.com.safetychampion.data.domain.usecase.activetask

import au.com.safetychampion.data.data.common.TaskRepository
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.action.ActiveTaskPL
import au.com.safetychampion.data.domain.models.task.Task

class GetAllActiveTaskUseCase(
    private val activeTaskRepository: TaskRepository
) {
    suspend operator fun invoke(moduleName: String?): Result<List<Task>> {
//        TODO("Use enum as moduleName instead String")
        val body = ActiveTaskPL(
            moduleName?.let { ActiveTaskPL.Modules(listOf(it)) }
        )
        return activeTaskRepository.getAllActiveTask(body)
    }
}
