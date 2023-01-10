package au.com.safetychampion.data.data.action

import au.com.safetychampion.data.data.BaseRepository
import au.com.safetychampion.data.domain.Attachment
import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.action.ActionTask
import au.com.safetychampion.data.domain.models.action.payload.ActionPL
import au.com.safetychampion.data.domain.models.action.payload.ActionSignOffPL
import au.com.safetychampion.data.domain.toMultipartBody
import au.com.safetychampion.data.domain.usecase.action.SignoffStatus
import au.com.safetychampion.util.koinInject

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

    override suspend fun list(body: BasePL?): Result<List<ActionPL>> {
        return apiCallAsList { api.list(body) }
    }

    override suspend fun signOff(
        actionId: String?,
        payload: ActionSignOffPL,
        photos: List<Attachment>?
    ): Result<SignoffStatus.OnlineCompleted> {
        return apiCall {
            api.signOff(
                actionId = actionId,
                body = payload.toRequestBody(),
                photos = photos.toMultipartBody(
                    fileManager = fileContentManager
                )
            )
        }
    }

    override suspend fun save(
        actionId: String?,
        payload: ActionSignOffPL,
        photos: List<Attachment>?
    ): Result<SignoffStatus.OnlineSaved> {
        return apiCall {
            api.signOff(
                actionId = actionId,
                body = payload.toRequestBody(),
                photos = photos.toMultipartBody(
                    fileManager = fileContentManager
                )
            )
        }
    }
}
