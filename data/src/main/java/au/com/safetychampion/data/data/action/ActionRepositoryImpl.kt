package au.com.safetychampion.data.data.action

import au.com.safetychampion.data.data.BaseRepository
import au.com.safetychampion.data.data.api.ActionApi
import au.com.safetychampion.data.domain.Attachment
import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.core.* // ktlint-disable no-wildcard-imports
import au.com.safetychampion.data.domain.models.SignoffStatus
import au.com.safetychampion.data.domain.models.action.ActionLink
import au.com.safetychampion.data.domain.models.action.ActionTask
import au.com.safetychampion.data.domain.models.action.network.ActionPL
import au.com.safetychampion.data.domain.models.action.network.ActionSignOffPL
import au.com.safetychampion.data.domain.usecase.action.ActionSignoffParams
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class ActionRepositoryImpl : BaseRepository(), IActionRepository {
    override suspend fun createAction(
        payload: ActionPL,
        attachments: List<Attachment>
    ): Result<ActionPL> {
        return ActionApi.New(payload, attachments).call()
    }

    override suspend fun createPendingAction(
        payload: ActionPL,
        attachments: List<Attachment>
    ): Result<ActionLink> {
        return ActionApi.New(payload, attachments).call()
    }

    override suspend fun fetchAction(taskId: String): Result<ActionPL> {
        return ActionApi.Fetch(taskId).call()
    }

    override suspend fun editAction(
        taskId: String,
        payload: ActionPL,
        attachments: List<Attachment>
    ): Result<Unit> {
        return ActionApi.Edit(taskId, payload, attachments).call()
    }

    override suspend fun task(taskId: String): Result<ActionTask> {
        return ActionApi.Task(taskId).call()
    }

    override suspend fun combineFetchAndTask(actionID: String): Result<ActionSignOffPL> {
        return withContext(dispatchers.io) {
            val actionFetch = async { fetchAction(actionID) }
            val taskFetch = async { task(actionID) }

            val fetch = actionFetch.await()
            val task = taskFetch.await()

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
                        data = ActionSignOffPL(
                            _id = _task._id,
                            moduleId = _fetch._id,
                            body = _fetch,
                            task = _task
                        )
                    )
                }
            }
        }
    }

    override suspend fun list(body: BasePL?): Result<List<ActionPL>> {
        return ActionApi.List(body).callAsList()
    }

    override suspend fun save(params: ActionSignoffParams): Result<SignoffStatus.OnlineSaved> {
        return ActionApi.Signoff(
            actionId = params.actionId,
            body = params.payload,
            photos = params.attachmentList ?: listOf()
        )
            .call<SignoffStatus.OnlineSaved>()
            .doOnSucceed {
                it.moduleName = ModuleName.ACTION.name
                it.title = "Title1234"
            }
    }

    override suspend fun signoff(params: ActionSignoffParams): Result<SignoffStatus.OnlineCompleted> {
        return ActionApi.Signoff(
            actionId = params.actionId,
            body = params.payload,
            photos = params.attachmentList ?: listOf()
        )
            .call<SignoffStatus.OnlineCompleted>()
            .doOnSucceed {
                it.moduleName = ModuleName.ACTION.name
                it.title = "Title1234"
            }
    }
}
