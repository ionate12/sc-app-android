package au.com.safetychampion.data.data.action

import au.com.safetychampion.data.domain.Attachment
import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.action.ActionTask
import au.com.safetychampion.data.domain.models.action.payload.ActionPL
import au.com.safetychampion.data.domain.models.action.payload.ActionSignOffPL

interface IActionRepository {
    suspend fun createNewAction(
        payload: ActionPL,
        attachments: List<Attachment>
    ): Result<ActionPL>

    suspend fun fetchAction(
        taskId: String?
    ): Result<ActionPL>

    suspend fun editAction(
        taskId: String?,
        payload: ActionPL,
        attachments: List<Attachment>
    ): Result<Unit>

    suspend fun task(
        taskId: String?
    ): Result<ActionTask>

    suspend fun list(body: BasePL?): Result<List<ActionPL>>

    suspend fun signOff(
        actionId: String?,
        payload: ActionSignOffPL,
        photos: List<Attachment>?
    ): Result<Unit>
}
