package au.com.safetychampion.data.domain.models.admin.database

import au.com.safetychampion.data.domain.Attachment
import au.com.safetychampion.data.domain.core.ModuleName
import au.com.safetychampion.data.domain.models.action.network.ActionSignOffPL
import au.com.safetychampion.data.domain.models.action.network.PendingActionPL
import au.com.safetychampion.data.domain.usecase.action.OfflineTaskInfo

class ActionSignOffEntity(
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
        get() = (if (payload.task.complete == true) "[SIGN-OFF]" else "[SAVE]") + "$moduleName Sign-off: " + title
}
