package au.com.safetychampion.data.data.safetyplan

import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.safetyplan.SafetyPlan
import au.com.safetychampion.data.domain.models.safetyplan.SafetyPlanSignoff
import au.com.safetychampion.data.domain.models.safetyplan.SafetyPlanTask

interface ISafetyPlanRepository {
    suspend fun fetch(safeId: String): Result<SafetyPlan>
    suspend fun task(
        safeId: String,
        taskId: String
    ): Result<SafetyPlanTask>
    suspend fun combineFetchAndTask(
        safeId: String,
        taskId: String
    ): Result<SafetyPlanSignoff>
    suspend fun signoff(
        payload: SafetyPlanTask
    ): Result<SafetyPlanSignoff>
}
