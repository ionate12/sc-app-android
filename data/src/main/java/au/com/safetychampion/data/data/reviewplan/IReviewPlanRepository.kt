package au.com.safetychampion.data.data.reviewplan

import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.action.Action
import au.com.safetychampion.data.domain.models.reviewplan.*
import au.com.safetychampion.data.domain.usecase.reviewplan.FilterRVPlanPL

interface IReviewPlanRepository {
    suspend fun getActions(reviewPlanId: String): Result<List<Action>>
    suspend fun list(filter: FilterRVPlanPL): Result<List<ReviewPlanReview>>
    suspend fun fetch(reviewPlanId: String): Result<ReviewPlanReview>
    suspend fun task(taskId: String, reviewPlanId: String): Result<ReviewPlanTask>
    suspend fun tasks(taskId: String): Result<ReviewPlanTask>
    suspend fun combineFetchAndTask(reviewPlanId: String, taskId: String): Result<ReviewPlanSignoff>
    suspend fun signoff(
        moduleId: String,
        taskId: String,
        payload: ReviewPlanSignoffTaskPL
    ): Result<ReviewPlanTask>
    suspend fun evidences(reviewPlanId: String): Result<List<ReviewPlanEvidenceTask>>
}
