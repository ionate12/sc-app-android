package au.com.safetychampion.data.data.contractor

import au.com.safetychampion.data.data.BaseRepository
import au.com.safetychampion.data.data.api.ContractorAPI
import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.contractor.ContractorLinkedTaskPL
import au.com.safetychampion.data.domain.models.contractor.ContractorLinkedTasks
import au.com.safetychampion.data.domain.models.contractor.ContractorProfile
import au.com.safetychampion.data.domain.models.task.Task

class ContractorRepositoryImpl : BaseRepository(), IContractorRepository {

    override suspend fun fetch(moduleId: String): Result<ContractorProfile> {
        return ContractorAPI.Fetch(moduleId).call()
    }

    override suspend fun list(body: BasePL?): Result<List<ContractorProfile>> {
        return ContractorAPI.List(body).callAsList()
    }

    override suspend fun linkedTasks(payload: ContractorLinkedTaskPL): Result<List<Task>> {
        return ContractorAPI.LinkedTasks(payload).callAsList()
    }
}
