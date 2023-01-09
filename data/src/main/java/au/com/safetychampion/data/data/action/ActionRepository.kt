package au.com.safetychampion.data.data.action

import au.com.safetychampion.data.domain.Attachment
import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.models.action.payload.Action
import au.com.safetychampion.data.domain.models.action.payload.ActionSignOff
import au.com.safetychampion.data.domain.models.action.ActionSignOffForm

interface ActionRepository {
    suspend fun createNewAction(
        payload: Action,
        attachments: List<Attachment>
    ): au.com.safetychampion.data.domain.core.Result<Action>

    suspend fun fetchAction(
        taskId: String?
    ): au.com.safetychampion.data.domain.core.Result<Action>

    suspend fun editAction(
        taskId: String?,
        payload: Action,
        attachments: List<Attachment>
    ): au.com.safetychampion.data.domain.core.Result<Unit>

    suspend fun task(
        taskId: String?
    ): au.com.safetychampion.data.domain.core.Result<ActionSignOffForm>

    suspend fun list(body: BasePL?): au.com.safetychampion.data.domain.core.Result<List<Action>>

    suspend fun signOff(
        actionId: String?,
        payload: ActionSignOff,
        photos: List<Attachment>?
    ): au.com.safetychampion.data.domain.core.Result<Unit>
}
