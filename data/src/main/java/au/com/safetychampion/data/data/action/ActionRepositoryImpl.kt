package au.com.safetychampion.data.data.action

import au.com.safetychampion.data.data.BaseRepository
import au.com.safetychampion.data.data.api.ActionApi
import au.com.safetychampion.data.domain.Attachment
import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.action.ActionLink
import au.com.safetychampion.data.domain.models.action.ActionTask
import au.com.safetychampion.data.domain.models.action.network.ActionPL
import au.com.safetychampion.data.domain.usecase.action.SignoffStatus

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

    override suspend fun list(body: BasePL?): Result<List<ActionPL>> {
        return ActionApi.List(body).callAsList()
    }

    override suspend fun signOff(
        actionId: String,
        payload: ActionTask,
        photos: List<Attachment>?
    ): Result<SignoffStatus.OnlineCompleted> {
        return ActionApi.SignOff(actionId, payload, photos ?: listOf()).call()
    }

    override suspend fun save(
        actionId: String,
        payload: ActionTask,
        photos: List<Attachment>?
    ): Result<SignoffStatus.OnlineSaved> {
        return ActionApi.SignOff(actionId, payload, photos ?: listOf()).call()
    }
}
