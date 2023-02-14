package au.com.safetychampion.data.domain.usecase.crisk

import au.com.safetychampion.data.data.crisk.ICriskRepository
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.contractor.ContractorLookup

class GetListContractorLookupUseCase(
    private val repository: ICriskRepository
) {
    suspend operator fun invoke(): Result<List<ContractorLookup>> {
        return repository.contractorLookup()
    }
}
