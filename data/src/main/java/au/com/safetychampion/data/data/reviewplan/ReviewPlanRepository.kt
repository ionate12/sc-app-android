package au.com.safetychampion.data.data.reviewplan

import au.com.safetychampion.data.data.BaseRepository
import au.com.safetychampion.data.data.api.ReviewPlanAPI
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.core.dataOrNull
import au.com.safetychampion.data.domain.core.flatMapError
import au.com.safetychampion.data.domain.core.map
import au.com.safetychampion.data.domain.models.action.Action
import au.com.safetychampion.data.domain.models.reviewplan.*
import au.com.safetychampion.data.domain.usecase.reviewplan.FilterRVPlanPL
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class ReviewPlanRepository : BaseRepository(), IReviewPlanRepository {
    override suspend fun getActions(reviewPlanId: String): Result<List<Action>> {
        return ReviewPlanAPI.Actions(reviewPlanId).call()
    }

    override suspend fun list(filter: FilterRVPlanPL): Result<List<ReviewPlanReview>> {
        return ReviewPlanAPI.List(filter).call()
    }

    override suspend fun fetch(reviewPlanId: String): Result<ReviewPlanReview> {
        return ReviewPlanAPI.Fetch(reviewPlanId).call()
    }

    override suspend fun task(taskId: String, reviewPlanId: String): Result<ReviewPlanTask> {
        return ReviewPlanAPI.Task(reviewPlanId, taskId).call()
    }

    override suspend fun tasks(taskId: String): Result<ReviewPlanTask> {
        return ReviewPlanAPI.Tasks(taskId).call()
    }

    override suspend fun combineFetchAndTask(
        reviewPlanId: String,
        taskId: String
    ): Result<ReviewPlanSignoff> {
        return withContext(dispatchers.io) {
            val taskCall = async { task(taskId, reviewPlanId).flatMapError { Result.Success(ReviewPlanTask()) } }
            val fetchCall = async { fetch(reviewPlanId) }

            val task = taskCall.await()
            val fetch = fetchCall.await()
            fetch.map {
                ReviewPlanSignoff(
                    body = fetch.dataOrNull()!!,
                    task = task.dataOrNull()!!
                )
            }
        }
    }

    override suspend fun signoff(
        moduleId: String,
        taskId: String,
        payload: ReviewPlanSignoffTaskPL
    ): Result<ReviewPlanTask> {
        return ReviewPlanAPI.SignOff(
            reviewPlanId = moduleId,
            taskId = taskId,
            payload = payload
        ).call()
    }

    override suspend fun evidences(reviewPlanId: String): Result<List<ReviewPlanEvidenceTask>> {
        return ReviewPlanAPI.EvidencesTask(reviewPlanId).call()
    }
}
