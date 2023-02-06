package au.com.safetychampion.data.domain.usecase.contractor

import au.com.safetychampion.data.data.contractor.IContractorRepository
import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.contractor.ContractorProfile

class GetListContractorUseCase(
    private val repository: IContractorRepository
) {
    suspend operator fun invoke(): Result<List<ContractorProfile>> {
        return repository.list(body = BasePL.empty())
    }
}