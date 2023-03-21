package au.com.safetychampion.data.domain.usecase.activetask

import au.com.safetychampion.data.data.common.ITaskRepository
import au.com.safetychampion.data.domain.core.ModuleName
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.action.network.ActiveTaskPL
import au.com.safetychampion.data.domain.models.task.Task

class GetAllActiveTaskUseCase(
    private val activeTaskRepository: ITaskRepository
) {
    suspend operator fun invoke(moduleName: ModuleName?): Result<List<Task>> {
        val body = ActiveTaskPL(
            moduleName?.let { ActiveTaskPL.Modules(listOf(it.code)) }
        )
        return activeTaskRepository.getAllActiveTask(body)
    }
}
