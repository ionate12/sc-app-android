package au.com.safetychampion.data.data.action

import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.action.Action
import au.com.safetychampion.data.domain.models.action.ActionTask
import au.com.safetychampion.data.domain.models.action.ActionTaskPL
import au.com.safetychampion.data.domain.models.action.network.ActionPL
import au.com.safetychampion.data.domain.models.action.network.ActionSignOff

interface IActionRepository {
    suspend fun createAction(
        payload: ActionPL
    ): Result<Action>

    suspend fun fetchAction(
        taskId: String
    ): Result<Action>

    suspend fun task(
        taskId: String
    ): Result<ActionTask>

    suspend fun combineFetchAndTask(actionID: String): Result<ActionSignOff>

    suspend fun editAction(
        actionId: String,
        payload: ActionPL
    ): Result<Action>

    suspend fun list(body: BasePL?): Result<List<Action>>

    suspend fun signoff(actionId: String, payload: ActionTaskPL): Result<ActionTask>
}
