package au.com.safetychampion.data.domain.usecase.contractor

import au.com.safetychampion.data.data.contractor.IContractorRepository
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.contractor.ContractorLinkedTaskPL
import au.com.safetychampion.data.domain.models.task.Task

class GetLinkedTaskUseCase(
    private val repository: IContractorRepository
) {
    suspend operator fun invoke(
        payload: ContractorLinkedTaskPL
    ):
        Result<List<Task>> {
        return repository.linkedTasks(
            payload = payload
        )
    }
}
