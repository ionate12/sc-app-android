package au.com.safetychampion.data.data.action

import au.com.safetychampion.data.domain.Attachment
import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.action.ActionLink
import au.com.safetychampion.data.domain.models.action.ActionTask
import au.com.safetychampion.data.domain.models.action.network.ActionPL
import au.com.safetychampion.data.domain.usecase.action.SignoffStatus

interface IActionRepository {
    suspend fun createAction(
        payload: ActionPL,
        attachments: List<Attachment>
    ): Result<ActionPL>

    suspend fun createPendingAction(
        payload: ActionPL,
        attachments: List<Attachment>
    ): Result<ActionLink>

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
        payload: ActionTask,
        photos: List<Attachment>?
    ): Result<SignoffStatus.OnlineCompleted>

    suspend fun save(
        actionId: String?,
        payload: ActionTask,
        photos: List<Attachment>?
    ): Result<SignoffStatus.OnlineSaved>
}
