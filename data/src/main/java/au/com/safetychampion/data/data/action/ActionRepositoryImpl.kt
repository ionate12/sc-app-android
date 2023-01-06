package au.com.safetychampion.data.data.action

import au.com.safetychampion.data.data.BaseRepository
import au.com.safetychampion.data.domain.Attachment
import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.action.ActionSignOffPL
import au.com.safetychampion.data.domain.models.action.ActionTaskPojo
import au.com.safetychampion.data.domain.payload.ActionPojo
import au.com.safetychampion.data.domain.toMultipartBody
import au.com.safetychampion.data.network.ActionAPI
import au.com.safetychampion.util.IFileManager

class ActionRepositoryImpl(
    private val api: ActionAPI,
    private val fileContentManager: IFileManager
) : BaseRepository(), ActionRepository {

    override suspend fun createNewAction(
        payload: ActionPojo,
        attachments: List<Attachment>
    ): Result<ActionPojo> {
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

    override suspend fun fetchAction(taskId: String?): Result<ActionPojo> {
        return apiCall { api.fetch(taskId) }
    }

    override suspend fun editAction(
        taskId: String?,
        payload: ActionPojo,
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

    override suspend fun task(taskId: String?): Result<ActionTaskPojo> {
        return apiCall { api.task(taskId) }
    }

    override suspend fun list(body: BasePL?): Result<List<ActionPojo>> {
        return apiCallAsList { api.list(body) }
    }

    override suspend fun signOff(
        actionId: String?,
        payload: ActionSignOffPL,
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
