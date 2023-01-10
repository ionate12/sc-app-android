package au.com.safetychampion.data.domain.usecase.action

import au.com.safetychampion.data.data.action.IActionRepository
import au.com.safetychampion.data.domain.Attachment
import au.com.safetychampion.data.domain.base.SignoffUseCase
import au.com.safetychampion.data.domain.core.ModuleName
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.core.dataOrNull
import au.com.safetychampion.data.domain.models.action.ActionLink
import au.com.safetychampion.data.domain.models.action.payload.ActionPL
import au.com.safetychampion.data.domain.models.action.payload.ActionSignOffPL
import au.com.safetychampion.data.domain.models.action.payload.PendingActionPL
import au.com.safetychampion.util.koinInject
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

sealed class SignoffStatus(val message: String) {
    class OfflineCompleted(
        val isOverwritten: Boolean? = false,
        moduleName: String,
        title: String
    ) : SignoffStatus(
        "OFFLINE - <b><u>$moduleName: $title/u></b> has been save/signed off as a new Offline Task. " +
            "\n**Offline tasks will be synchronised when the device is back online "
    )
    class OnlineCompleted(moduleName: String, title: String) : SignoffStatus(
        "You've successfully signed off <b><u>$moduleName: $title</u></b>"
    )
    class OnlineSaved(moduleName: String, title: String) : SignoffStatus(
        "You've successfully saved <b><u>$moduleName: $title</u></b>"
    )
}

class SignOffActionUseCase : SignoffUseCase<ActionSignOffParam>() {
    private val repository: IActionRepository by koinInject()

    private val pendingActionCall: suspend (ActionPL, List<Attachment>) -> ActionLink? = { action, attachments ->
        repository.createAction(action, attachments).dataOrNull()?.let {
            ActionLink(type = it.type, _id = it._id)
        }
    }

    override val signoffCall: suspend (ActionSignOffParam) -> Result<SignoffStatus> = { signOffParam ->
        withContext(dispatchers.io) {
            signOffParam.pendingAction
                .map {
                    async { pendingActionCall.invoke(it.action, it.attachment) }
                }
                .awaitAll()
                .filterNotNull()
                .let {
                    signOffParam.pendingAction.clear()
                    signOffParam.payload.task.links.clear()
                    signOffParam.payload.task.links.addAll(it)
                }

            repository.signOff(
                signOffParam.actionId,
                signOffParam.payload,
                signOffParam.attachments
            )
        }
    }
}

class ActionSignOffParam(
    val actionId: String,
    val attachments: List<Attachment>,
    val payload: ActionSignOffPL,
    val pendingAction: MutableList<PendingActionPL>,
    override val id: String
) : OfflineTaskInfo {
    override val moduleName: ModuleName
        get() = ModuleName.ACTION
    override val title: String
        get() = "1234"
    override val offlineTitle: String
        get() = (if (payload.task.complete) "[SIGN-OFF]" else "[SAVE]") + "$moduleName Sign-off: " + title
}

interface OfflineTaskInfo {
    val id: String
    val moduleName: ModuleName
    val title: String
    val offlineTitle: String
}

class OfflineTask(
    var title: String = "",
    var status: String = ""
)
