package au.com.safetychampion.data.domain.usecase.hr

import au.com.safetychampion.data.data.hr.IHrRepository
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.hr.HRProfile
import au.com.safetychampion.data.domain.usecase.BaseUseCase
import au.com.safetychampion.util.koinInject

class FetchHrDetailUseCase : BaseUseCase() {
    private val repository: IHrRepository by koinInject()
    suspend operator fun invoke(
        moduleId: String
    ): Result<HRProfile> {
        return repository.fetch(moduleId)
    }
}
