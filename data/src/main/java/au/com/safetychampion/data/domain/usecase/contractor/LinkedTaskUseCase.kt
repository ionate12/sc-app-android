package au.com.safetychampion.data.domain.usecase.contractor

import au.com.safetychampion.data.data.contractor.IContractorRepository
import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.task.Task

class LinkedTaskUseCase(
    private val repository: IContractorRepository
) {
    suspend operator fun invoke(
        body: BasePL
    ):
        Result<List<Task>> {
        return repository.linkedTasks(
            body = body
        )
    }
}
