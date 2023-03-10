package au.com.safetychampion.data.domain.usecase.hr

import au.com.safetychampion.data.data.hr.IHrRepository
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.hr.HRProfile
import au.com.safetychampion.data.domain.usecase.BaseUseCase
import au.com.safetychampion.data.util.extension.koinInject

class GetListHrUseCase : BaseUseCase() {
    private val repository: IHrRepository by koinInject()
    suspend operator fun invoke(): Result<List<HRProfile>> {
        return repository.list()
    }
}
