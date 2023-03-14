package au.com.safetychampion.data.data.action

import au.com.safetychampion.data.data.BaseRepository
import au.com.safetychampion.data.data.api.ActionApi
import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.core.SCError
import au.com.safetychampion.data.domain.core.dataOrNull
import au.com.safetychampion.data.domain.core.errorOrNull
import au.com.safetychampion.data.domain.models.action.Action
import au.com.safetychampion.data.domain.models.action.ActionTask
import au.com.safetychampion.data.domain.models.action.ActionTaskPL
import au.com.safetychampion.data.domain.models.action.network.ActionNewPL
import au.com.safetychampion.data.domain.models.action.network.ActionSignOff
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class ActionRepository : BaseRepository(), IActionRepository {
    override suspend fun createAction(
        payload: ActionNewPL
    ): Result<Action> {
        return ActionApi.New(payload).call()
    }

    override suspend fun fetchAction(taskId: String): Result<Action> {
        return ActionApi.Fetch(taskId).call()
    }

    override suspend fun editAction(
        actionId: String,
        payload: ActionNewPL
    ): Result<Action> {
        return ActionApi.Edit(actionId, payload).call()
    }

    override suspend fun task(taskId: String): Result<ActionTask> {
        return ActionApi.Task(taskId).call()
    }

    override suspend fun combineFetchAndTask(actionID: String): Result<ActionSignOff> {
        return withContext(dispatchers.io) {
            val actionFetch = async { fetchAction(actionID) }
            val taskFetch = async { task(actionID) }

            val fetch = actionFetch.await()
            val task = taskFetch.await()

            // TODO: Need refactor precondition checks!!
            return@withContext when {
                fetch.errorOrNull() is SCError.NoNetwork || task.errorOrNull() is SCError.NoNetwork -> Result.Error(
                    SCError.NoNetwork
                )
                fetch is Result.Error || task is Result.Error -> {
                    Result.Error(err = fetch.errorOrNull() ?: task.errorOrNull()!!)
                }
                else -> {
                    val _task = task.dataOrNull()!!
                    val _fetch = fetch.dataOrNull()!!
                    Result.Success(
                        data = ActionSignOff(
                            body = _fetch,
                            task = _task
                        )
                    )
                }
            }
        }
    }

    override suspend fun list(body: BasePL?): Result<List<Action>> {
        return ActionApi.List(body).call()
    }

    override suspend fun signoff(actionId: String, payload: ActionTaskPL): Result<ActionTask> {
        return ActionApi.Signoff(
            actionId = actionId,
            body = payload
        ).call()
    }
}
