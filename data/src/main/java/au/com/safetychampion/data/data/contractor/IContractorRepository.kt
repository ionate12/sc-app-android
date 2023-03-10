package au.com.safetychampion.data.data.contractor

import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.contractor.ContractorLinkedTaskPL
import au.com.safetychampion.data.domain.models.contractor.ContractorProfile
import au.com.safetychampion.data.domain.models.task.Task

interface IContractorRepository {
    suspend fun fetch(moduleId: String): Result<ContractorProfile>
    suspend fun list(): Result<List<ContractorProfile>>
    suspend fun linkedTasks(payload: ContractorLinkedTaskPL): Result<List<Task>>
}
