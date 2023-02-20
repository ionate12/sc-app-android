package au.com.safetychampion.data.domain.usecase.safetyplan

import au.com.safetychampion.data.data.safetyplan.ISafetyPlanRepository
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.safetyplan.SafetyPlan
import au.com.safetychampion.data.domain.usecase.BaseSignoffUseCase

class PrepareSafetyPlanSignoffUseCase(
    val repository: ISafetyPlanRepository
) : BaseSignoffUseCase() {

    suspend fun invoke(safeId: String): Result<SafetyPlan> {
        return repository.fetch(safeId)
    }
}
