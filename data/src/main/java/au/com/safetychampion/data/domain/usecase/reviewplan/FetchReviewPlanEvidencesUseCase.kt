package au.com.safetychampion.data.domain.usecase.reviewplan

import au.com.safetychampion.data.data.reviewplan.IReviewPlanRepository
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.reviewplan.ReviewPlanEvidenceTask

class FetchReviewPlanEvidencesUseCase(
    private val repository: IReviewPlanRepository
) {
    suspend fun invoke(reviewPlanId: String): Result<List<ReviewPlanEvidenceTask>> {
        return repository.evidences(reviewPlanId)
    }
}
