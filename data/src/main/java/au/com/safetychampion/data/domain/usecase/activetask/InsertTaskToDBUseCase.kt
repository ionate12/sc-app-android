package au.com.safetychampion.data.domain.usecase.activetask

import au.com.safetychampion.data.data.common.TaskDAO
import au.com.safetychampion.data.domain.models.task.Task

class InsertTaskToDBUseCase(
    private val repository: TaskDAO
) {
    operator fun invoke(tasks: List<Task>) {
//       + repository.deleteAllTask
        repository.insert(*tasks.toTypedArray())
    }
}
