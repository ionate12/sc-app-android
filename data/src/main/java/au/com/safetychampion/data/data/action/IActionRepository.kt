package au.com.safetychampion.data.data.action

import au.com.safetychampion.data.domain.Attachment
import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.SignoffStatus
import au.com.safetychampion.data.domain.models.action.ActionLink
import au.com.safetychampion.data.domain.models.action.ActionTask
import au.com.safetychampion.data.domain.models.action.network.ActionPL
import au.com.safetychampion.data.domain.models.action.network.ActionSignOffPL
import au.com.safetychampion.data.domain.usecase.ISignoffGeneral
import au.com.safetychampion.data.domain.usecase.action.ActionSignoffParams

interface IActionRepository : ISignoffGeneral<ActionSignoffParams> {
    suspend fun createAction(
        payload: ActionPL,
        attachments: List<Attachment>
    ): Result<ActionPL>

    suspend fun createPendingAction(
        payload: ActionPL,
        attachments: List<Attachment>
    ): Result<ActionLink>

    suspend fun fetchAction(
        taskId: String
    ): Result<ActionPL>

    suspend fun task(
        taskId: String
    ): Result<ActionTask>

    suspend fun combineFetchAndTask(actionID: String): Result<ActionSignOffPL>

    suspend fun editAction(
        taskId: String,
        payload: ActionPL,
        attachments: List<Attachment>
    ): Result<Unit>

    suspend fun list(body: BasePL?): Result<List<ActionPL>>

    override suspend fun save(params: ActionSignoffParams): Result<SignoffStatus.OnlineSaved>

    override suspend fun signoff(params: ActionSignoffParams): Result<SignoffStatus.OnlineCompleted>
}
