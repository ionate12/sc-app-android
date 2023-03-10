package au.com.safetychampion.data.domain.usecase.reviewplan

import au.com.safetychampion.data.data.reviewplan.IReviewPlanRepository
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.action.Action

class FetchActionsWithReviewPlanIdUseCase(
    private val repository: IReviewPlanRepository
) {
    suspend fun invoke(reviewPlanId: String): Result<List<Action>> {
        return repository.getActions(reviewPlanId)
    }
}
