package au.com.safetychampion.data.data.action

import au.com.safetychampion.data.domain.Attachment
import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.models.action.ActionPojo
import au.com.safetychampion.data.domain.models.action.ActionSignOffPL
import au.com.safetychampion.data.domain.models.action.ActionTaskPojo

interface ActionRepository {
    suspend fun createNewAction(
        payload: ActionPojo,
        attachments: List<Attachment>
    ): au.com.safetychampion.data.domain.core.Result<ActionPojo>

    suspend fun fetchAction(
        taskId: String?
    ): au.com.safetychampion.data.domain.core.Result<ActionPojo>

    suspend fun editAction(
        taskId: String?,
        payload: ActionPojo,
        attachments: List<Attachment>
    ): au.com.safetychampion.data.domain.core.Result<Unit>

    suspend fun task(
        taskId: String?
    ): au.com.safetychampion.data.domain.core.Result<ActionTaskPojo>

    suspend fun list(body: BasePL?): au.com.safetychampion.data.domain.core.Result<List<ActionPojo>>

    suspend fun signOff(
        actionId: String?,
        payload: ActionSignOffPL,
        photos: List<Attachment>?
    ): au.com.safetychampion.data.domain.core.Result<Unit>
}
