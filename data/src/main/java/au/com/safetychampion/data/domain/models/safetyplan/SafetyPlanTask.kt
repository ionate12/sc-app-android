package au.com.safetychampion.data.domain.models.safetyplan

import androidx.lifecycle.MutableLiveData
import au.com.safetychampion.data.domain.Attachment
import au.com.safetychampion.data.domain.base.BaseModule
import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.base.BaseTask
import au.com.safetychampion.data.domain.models.IAttachment
import au.com.safetychampion.data.domain.models.IPendingActionPL
import au.com.safetychampion.data.domain.models.Tier
import au.com.safetychampion.data.domain.models.action.ActionLink
import au.com.safetychampion.data.domain.models.action.network.ActionPL
import au.com.safetychampion.data.domain.models.action.network.PendingActionPL
import au.com.safetychampion.data.domain.uncategory.Constants
import au.com.safetychampion.data.domain.uncategory.DocAttachment
import com.google.gson.annotations.SerializedName

data class SafetyPlanTask(
    var attachments: MutableList<DocAttachment> = mutableListOf(),
    override var complete: Boolean = false,
    var completionNotes: String? = null,
    var dateCompleted: String? = null,
    override var dateDue: String? = null,
    var dateDueFrom: String = "DATE_DUE",
    override var description: String? = null,
    var links: MutableLiveData<MutableList<ActionLink>> = MutableLiveData(mutableListOf()),
    override val inExecution: Boolean?,
    var numTaskAssignees: Long? = null,
    var newActions: MutableLiveData<MutableList<ActionPL>> = MutableLiveData(mutableListOf()),
    var tzDateSignedoff: String = Constants.tz,
    override var title: String? = null
) : BasePL(), BaseTask {
    override lateinit var type: String
    override lateinit var _id: String

    @SerializedName("for")
    override lateinit var _for: BaseModule
    override lateinit var tier: Tier
}

data class SafetyPlanTaskPL(
    override var attachments: MutableList<Attachment> = mutableListOf(),
    var completionNotes: String? = null,
    var dateCompleted: String? = null,
    var tzDateSignedoff: String? = null,
    var dateDueFrom: String? = null,
    var links: List<ActionLink>? = null,
    var complete: Boolean = false,
    override var pendingActions: MutableList<PendingActionPL>
) : BasePL(), IAttachment, IPendingActionPL {
    override fun onPendingActionsCreated(links: List<ActionLink>): BasePL {
//        this.
        TODO()
    }
}
