package au.com.safetychampion.data.domain.usecase.reviewplan

import au.com.safetychampion.data.data.reviewplan.IReviewPlanRepository
import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.reviewplan.ReviewPlanReview

class FetchListReviewPlanUseCase(
    private val repository: IReviewPlanRepository
) {
    suspend fun invoke(filter: FilterRVPlanPL = FilterRVPlanPL()): Result<List<ReviewPlanReview>> {
        return repository.list(filter)
    }
}

data class ListReviewPlanFilter(
    val archived: Boolean = false
)

data class FilterRVPlanPL(
    val filter: ListReviewPlanFilter = ListReviewPlanFilter()
) : BasePL()
