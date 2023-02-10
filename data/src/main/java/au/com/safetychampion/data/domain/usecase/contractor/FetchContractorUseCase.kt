package au.com.safetychampion.data.domain.usecase.contractor

import android.util.Log
import au.com.safetychampion.data.data.contractor.IContractorRepository
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.contractor.ContractorProfile

class FetchContractorUseCase(
    private val repository: IContractorRepository
) {
    suspend operator fun invoke(
        moduleId: String
    ): Result<ContractorProfile> {
        return repository.fetch(moduleId = moduleId)
    }
}
