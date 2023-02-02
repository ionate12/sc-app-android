package au.com.safetychampion.data.data.contractor

import au.com.safetychampion.data.data.BaseRepository
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.contractor.ContractorProfile
import au.com.safetychampion.util.koinInject

class ContractorRepositoryImpl:BaseRepository(), IContractorRepository {
    private val api: ContractorAPI by koinInject()

    override suspend fun fetch(moduleId: String): Result<ContractorProfile> {
        TODO("Not yet implemented")
    }


}