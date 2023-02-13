package au.com.safetychampion.data.domain.models.action

import au.com.safetychampion.data.domain.Attachment
import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.models.ICategoryCusval
import au.com.safetychampion.data.domain.models.ICusval
import au.com.safetychampion.data.domain.models.IPendingActionPL
import au.com.safetychampion.data.domain.models.action.network.PendingActionPL
import au.com.safetychampion.data.domain.models.customvalues.CustomValue

data class ActionTaskPL(
    val dateCompleted: String,
    val completionNotes: String,
    val hazardCategory: String,
    val hazardCategoryOther: String? = null,
    val complete: Boolean,
    val tzDateSignedoff: String,
    val attachments: List<Attachment> = listOf(),
    val severity: String,
    val controlLevel: String,
    val controlLevelOther: String? = null,
    val links: List<ActionLink> = listOf(),
    override var cusvals: MutableList<CustomValue>,
    override var categoryCusvals: MutableList<CustomValue>,
    override var pendingActions: MutableList<PendingActionPL>
) : BasePL(), ICusval, ICategoryCusval, IPendingActionPL {
    companion object {
        fun fromModel(model: ActionTask): ActionTaskPL {
            TODO("Implement Action Task -> ActionTaskPL")
        }
    }

    override fun onPendingActionsCreated(links: List<ActionLink>): ActionTaskPL {
        return this.copy(links = this.links + links)
    }
}
