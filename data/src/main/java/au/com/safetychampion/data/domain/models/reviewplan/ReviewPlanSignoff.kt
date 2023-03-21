package au.com.safetychampion.data.domain.models.reviewplan

import au.com.safetychampion.data.domain.base.BaseSignOff
import au.com.safetychampion.data.domain.core.ModuleType

data class ReviewPlanSignoff(
    var body: ReviewPlanReview,
    override var task: ReviewPlanTask
) : BaseSignOff<ReviewPlanTask> {
    override fun syncableKey(): String = BaseSignOff.reviewPlanSignoffSyncableKey(body._id!!, taskId = task._id!!)

    override fun type(): ModuleType = ModuleType.REVIEW_PLAN
}
