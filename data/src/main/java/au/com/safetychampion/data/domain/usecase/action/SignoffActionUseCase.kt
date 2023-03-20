package au.com.safetychampion.data.domain.usecase.action

import au.com.safetychampion.data.data.action.IActionRepository
import au.com.safetychampion.data.domain.Attachment
import au.com.safetychampion.data.domain.base.BaseTask
import au.com.safetychampion.data.domain.core.ModuleType
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.core.Signature
import au.com.safetychampion.data.domain.models.action.ActionTask
import au.com.safetychampion.data.domain.models.action.ActionTaskPL
import au.com.safetychampion.data.domain.models.action.network.ActionSignOff
import au.com.safetychampion.data.domain.models.action.network.PendingActionPL
import au.com.safetychampion.data.domain.usecase.BaseSignoffUseCase
import au.com.safetychampion.data.util.extension.koinInject

class SignoffActionUseCase : BaseSignoffUseCase<ActionTask, ActionSignOff>() {
    private val repo: IActionRepository by koinInject()

    override suspend fun signoffOnline(payload: ActionSignOff): Result<ActionTask> {
        return repo.signoff(payload.body._id, ActionTaskPL.fromModel(payload.task))
    }
}

class ActionSignoffParams(
    val actionId: String,
    override val attachmentList: List<Attachment>,
    override val moduleType: ModuleType,
    override val payload: ActionTask,
    override val signaturesList: List<Signature>?,
    override val title: String
) : SignoffParams()

/** Base class for all signoff call */
abstract class SignoffParams {

    /** Task id, used to retrieve offline task */
    final val id: String get() = payload._id ?: ""

    abstract val attachmentList: List<Attachment>

    abstract val moduleType: ModuleType

    /** Payload for signoff */
    abstract val payload: BaseTask

    abstract val signaturesList: List<Signature>?

    abstract val title: String

    /** Pending action, will be submit before sign off. Can be null */
    open val pendingAction: List<PendingActionPL> = listOf()

    val offlineTitle: String get() = if (payload.complete == true) "[SIGN-OFF]" else "[SAVE] ${moduleType.title}: $title"
}

class OfflineTask(
    var title: String = "",
    var status: String = ""
)
