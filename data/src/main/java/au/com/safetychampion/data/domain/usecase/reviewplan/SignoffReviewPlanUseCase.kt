package au.com.safetychampion.data.domain.usecase.reviewplan

import au.com.safetychampion.data.data.reviewplan.IReviewPlanRepository
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.reviewplan.ReviewPlanSignoff
import au.com.safetychampion.data.domain.models.reviewplan.ReviewPlanSignoffTaskPL
import au.com.safetychampion.data.domain.models.reviewplan.ReviewPlanTask
import au.com.safetychampion.data.domain.usecase.BaseSignoffUseCase

class SignoffReviewPlanUseCase(private val repository: IReviewPlanRepository) : BaseSignoffUseCase<ReviewPlanTask, ReviewPlanSignoff>() {
    override suspend fun signoffOnline(payload: ReviewPlanSignoff): Result<ReviewPlanTask> {
        return repository.signoff(
            moduleId = payload.body._id,
            taskId = payload.task._id!!,
            payload = ReviewPlanSignoffTaskPL.fromModel(payload.task)
        )
    }
}
