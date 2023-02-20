package au.com.safetychampion.data.data.safetyplan

import au.com.safetychampion.data.data.BaseRepository
import au.com.safetychampion.data.data.api.SafetyPlanAPI
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.core.dataOrNull
import au.com.safetychampion.data.domain.models.safetyplan.SafetyPlan
import au.com.safetychampion.data.domain.models.safetyplan.SafetyPlanSignoff
import au.com.safetychampion.data.domain.models.safetyplan.SafetyPlanTask
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class SafetyPlanRepository : BaseRepository(), ISafetyPlanRepository {
    override suspend fun fetch(safeId: String): Result<SafetyPlan> {
        return SafetyPlanAPI.Fetch(safeId).call()
    }

    override suspend fun task(
        safeId: String,
        taskId: String
    ): Result<SafetyPlanTask> {
        return SafetyPlanAPI.FetchTask(
            safeId = safeId,
            taskId = taskId
        ).call()
    }

    @Throws(IllegalArgumentException::class)
    override suspend fun combineFetchAndTask(
        safeId: String,
        taskId: String
    ): Result<SafetyPlanSignoff> {
        return withContext(dispatchers.io) {
            val fetchCall = async { fetch(safeId) }
            val taskCall = async { task(safeId, taskId) }
            val fetch = fetchCall.await().dataOrNull()
            val task = taskCall.await().dataOrNull()
            require(fetch != null && task != null)
            Result.Success(
                SafetyPlanSignoff(
                    body = fetch,
                    task = task
                )
            )
        }
    }

    override suspend fun signoff(payload: SafetyPlanTask): Result<SafetyPlanSignoff> {
        TODO("Not yet implemented")
    }
}
