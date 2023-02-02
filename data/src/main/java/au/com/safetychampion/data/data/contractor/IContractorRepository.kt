package au.com.safetychampion.data.data.contractor

import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.contractor.ContractorProfile

interface IContractorRepository {
    suspend fun fetch(moduleId: String): Result<ContractorProfile>
}
