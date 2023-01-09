package au.com.safetychampion.data.data.action

import au.com.safetychampion.data.data.BaseRepository
import au.com.safetychampion.data.domain.Attachment
import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.manager.IFileManager
import au.com.safetychampion.data.domain.models.action.payload.Action
import au.com.safetychampion.data.domain.models.action.payload.ActionSignOff
import au.com.safetychampion.data.domain.models.action.ActionSignOffForm
import au.com.safetychampion.data.domain.toMultipartBody

class ActionRepositoryImpl(
    private val api: ActionAPI,
    private val fileContentManager: IFileManager
) : BaseRepository(), ActionRepository {

    override suspend fun createNewAction(
        payload: Action,
        attachments: List<Attachment>
    ): Result<Action> {
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

    override suspend fun fetchAction(taskId: String?): Result<Action> {
        return apiCall { api.fetch(taskId) }
    }

    override suspend fun editAction(
        taskId: String?,
        payload: Action,
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

    override suspend fun task(taskId: String?): Result<ActionSignOffForm> {
        return apiCall { api.task(taskId) }
    }

    override suspend fun list(body: BasePL?): Result<List<Action>> {
        return apiCallAsList { api.list(body) }
    }

    override suspend fun signOff(
        actionId: String?,
        payload: ActionSignOff,
        photos: List<Attachment>?
    ): Result<Unit> {
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
