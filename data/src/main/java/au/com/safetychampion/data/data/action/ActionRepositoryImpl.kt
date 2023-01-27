package au.com.safetychampion.data.data.action

import au.com.safetychampion.data.data.BaseRepository
import au.com.safetychampion.data.domain.Attachment
import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.core.* // ktlint-disable no-wildcard-imports
import au.com.safetychampion.data.domain.models.SignoffStatus
import au.com.safetychampion.data.domain.models.action.ActionLink
import au.com.safetychampion.data.domain.models.action.ActionTask
import au.com.safetychampion.data.domain.models.action.network.ActionPL
import au.com.safetychampion.data.domain.models.action.network.ActionSignOffPL
import au.com.safetychampion.data.domain.toMultipartBody
import au.com.safetychampion.util.koinInject
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class ActionRepositoryImpl : BaseRepository(), IActionRepository {
    private val api: ActionAPI by koinInject()

    override suspend fun createAction(
        payload: ActionPL,
        attachments: List<Attachment>
    ): Result<ActionPL> {
        return apiCall(
            call = {
                api.newAction(
                    body = payload.toRequestBody(),
                    photos = attachments.toMultipartBody(
                        fileManager = fileContentManager
                    )
                )
            }
        )
    }

    override suspend fun createPendingAction(
        payload: ActionPL,
        attachments: List<Attachment>
    ): Result<ActionLink> {
        return apiCall(
            call = {
                api.newAction(
                    body = payload.toRequestBody(),
                    photos = attachments.toMultipartBody(
                        fileManager = fileContentManager
                    )
                )
            }
        )
    }

    override suspend fun fetchAction(taskId: String?): Result<ActionPL> {
        return apiCall { api.fetch(taskId) }
    }

    override suspend fun editAction(
        taskId: String?,
        payload: ActionPL,
        attachments: List<Attachment>
    ): Result<Unit> {
        return apiCall {
            api.editAction(
                taskId = taskId,
                body = payload.toRequestBody(),
                photos = attachments.toMultipartBody(
                    fileManager = fileContentManager
                )
            )
        }
    }

    override suspend fun task(taskId: String?): Result<ActionTask> {
        return apiCall { api.task(taskId) }
    }

    override suspend fun combineFetchAndTask(actionID: String): Result<ActionSignOffPL> {
        return withContext(dispatchers.io) {
            val actionFetch = async { fetchAction(actionID) }
            val taskFetch = async { task(actionID) }

            val fetch = actionFetch.await()
            val task = taskFetch.await()

            return@withContext when {
                fetch.errorOrNull() is SCError.NoNetwork || task.errorOrNull() is SCError.NoNetwork -> Result.Error(
                    SCError.NoNetwork()
                )
                fetch is Result.Error || task is Result.Error -> { Result.Error(err = fetch.errorOrNull() ?: task.errorOrNull()!!) }
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
        return apiCallAsList { api.list(body) }
    }

    override suspend fun signoff(
        actionId: String?,
        payload: ActionTask,
        photos: List<Attachment>?
    ): Result<SignoffStatus.OnlineCompleted> {
        return apiCall<SignoffStatus.OnlineCompleted> {
            api.signOff(
                actionId = actionId,
                body = payload.toRequestBody(),
                photos = photos.toMultipartBody(
                    fileManager = fileContentManager
                )
            )
        }.doOnSucceed {
            it.moduleName = ModuleName.ACTION.name
            it.title = "Title1234"
        }
    }

    override suspend fun save(
        actionId: String?,
        payload: ActionTask,
        photos: List<Attachment>?
    ): Result<SignoffStatus.OnlineSaved> {
        return apiCall<SignoffStatus.OnlineSaved> {
            api.signOff(
                actionId = actionId,
                body = payload.toRequestBody(),
                photos = photos.toMultipartBody(
                    fileManager = fileContentManager
                )
            )
        }.doOnSucceed {
            it.moduleName = ModuleName.ACTION.name
            it.title = "Title1234"
        }
    }
}
