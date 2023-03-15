package au.com.safetychampion.data.domain.usecase.reviewplan

import au.com.safetychampion.data.data.reviewplan.IReviewPlanRepository
import au.com.safetychampion.data.domain.base.BaseSignOff
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.reviewplan.ReviewPlanSignoff
import au.com.safetychampion.data.domain.models.reviewplan.ReviewPlanTask
import au.com.safetychampion.data.domain.usecase.BasePrepareSignoffUseCase

class PrepareReviewPlanSignoffUseCase(
    private val repository: IReviewPlanRepository
) : BasePrepareSignoffUseCase<ReviewPlanTask, ReviewPlanSignoff>() {

    override suspend fun fetchData(moduleId: String, taskId: String?): Result<ReviewPlanSignoff> {
        requireNotNull(taskId)
        return repository.combineFetchAndTask(moduleId, taskId)
    }

    override fun getSyncableKey(moduleId: String, taskId: String?): String {
        requireNotNull(taskId)
        return BaseSignOff.reviewPlanSignoffSyncableKey(moduleId, taskId)
    }
}
