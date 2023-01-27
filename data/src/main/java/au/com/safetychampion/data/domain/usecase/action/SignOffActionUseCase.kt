package au.com.safetychampion.data.domain.usecase.action

import au.com.safetychampion.data.data.action.IActionRepository
import au.com.safetychampion.data.domain.Attachment
import au.com.safetychampion.data.domain.core.ModuleName
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.core.dataOrNull
import au.com.safetychampion.data.domain.models.SignoffStatus
import au.com.safetychampion.data.domain.models.action.ActionTask
import au.com.safetychampion.data.domain.models.action.network.PendingActionPL
import au.com.safetychampion.data.domain.usecase.BaseSignoffUseCase
import au.com.safetychampion.util.koinInject
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

class SignOffActionUseCase : BaseSignoffUseCase<ActionSignOffParam>() {

    private val repository: IActionRepository by koinInject()

    private val createPendingActionUseCase: CreatePendingActionUseCase by koinInject()

    override suspend fun signoffInternal(param: ActionSignOffParam): Result<SignoffStatus> {
        return withContext(dispatchers.io) {
            param.pendingAction
                ?.map {
                    async {
                        createPendingActionUseCase.invoke(
                            payload = it.action,
                            attachments = it.attachment
                        ).dataOrNull()
                    }
                }
                ?.awaitAll()
                ?.filterNotNull() // Currently, we're ignoring the pending action result, if success -> add to task.link else do nothing
                ?.let {
                    param.payload.links?.clear()
                    param.payload.links?.addAll(it)
                    param.pendingAction = null
                }

            if (param.payload.complete == true) {
                repository.signoff(
                    param.actionId,
                    param.payload,
                    param.attachments
                )
            } else {
                repository.save(
                    param.actionId,
                    param.payload,
                    param.attachments
                )
            }
        }
    }
}

class ActionSignOffParam(
    val actionId: String,
    val attachments: List<Attachment>,
    val payload: ActionTask,
    var pendingAction: List<PendingActionPL>?,
    override val id: String
) : OfflineTaskInfo {
    override val moduleName: ModuleName
        get() = ModuleName.ACTION
    override val title: String
        get() = "1234"
    override val offlineTitle: String
        get() = (if (payload.complete == true) "[SIGN-OFF]" else "[SAVE]") + "$moduleName Sign-off: " + title
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
