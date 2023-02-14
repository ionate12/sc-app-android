package au.com.safetychampion.data.domain.usecase.crisk

import au.com.safetychampion.data.data.crisk.ICriskRepository
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.hr.HrLookupItem

class GetListHrLookupItemUseCase(
    private val repository: ICriskRepository
) {
    suspend operator fun invoke(): Result<List<HrLookupItem>> {
        return repository.hrLookup()
    }
}
