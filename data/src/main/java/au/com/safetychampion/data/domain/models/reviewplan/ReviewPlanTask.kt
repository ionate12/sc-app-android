package au.com.safetychampion.data.domain.models.reviewplan

import au.com.safetychampion.data.domain.Attachment
import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.base.BaseTask
import au.com.safetychampion.data.domain.core.Signature
import au.com.safetychampion.data.domain.models.*
import au.com.safetychampion.data.domain.models.action.ActionLink
import au.com.safetychampion.data.domain.models.action.network.PendingActionPL
import au.com.safetychampion.data.domain.models.customvalues.CustomValue
import com.google.gson.annotations.SerializedName

interface IReviewPlanComponent : ICusval, ISubcategoryCusval, ISignature, IAttachment

data class ReviewPlanSignoffTaskPL(
    override var attachments: MutableList<Attachment>,
    val actionLinks: MutableList<ActionLink>,
    val complete: Boolean,
    override var cusvals: MutableList<CustomValue>,
    val dateCompleted: String?,
    val dateIssued: String?,
    val dateExpiry: String?,
    val reviewNotes: String?,
    val recurrent: Boolean?,
    val referenceId: String?,
    val notes: String?,
    override var subcategoryCusvals: MutableList<CustomValue>,
    override var signatures: MutableList<Signature>,
    val tzDateSignedoff: String?,
    override var pendingActions: MutableList<PendingActionPL>
) : BasePL(), IReviewPlanComponent, IPendingActionPL {
    companion object {
        fun fromModel(model: ReviewPlanTask): ReviewPlanSignoffTaskPL {
            return ReviewPlanSignoffTaskPL(
                attachments = model.attachments,
                complete = model.complete ?: false,
                cusvals = model.cusvals,
                dateCompleted = model.dateCompleted,
                dateIssued = model.dateIssued,
                dateExpiry = model.dateExpiry,
                reviewNotes = model.reviewNotes,
                recurrent = model.recurrent,
                referenceId = model.referenceId,
                notes = model.notes,
                subcategoryCusvals = model.subcategoryCusvals,
                signatures = model.signatures,
                actionLinks = mutableListOf(),
                pendingActions = model.pendingActions,
                tzDateSignedoff = model.tzDateSignedoff
            )
        }
    }
    override fun onPendingActionsCreated(links: List<ActionLink>): BasePL {
        return this.copy(actionLinks = (actionLinks + links).toMutableList())
    }
}

data class ReviewPlanTask(
    override val complete: Boolean? = true,
    override val dateDue: String? = null,
    override val description: String? = null,
    @SerializedName("for")
    override val _for: ReviewPlanReview? = null,
    override val inExecution: Boolean? = false,
    override val tier: Tier? = null,
    override val title: String? = null,
    override val _id: String? = null,
    override val type: String? = null,
    val tzDateSignedoff: String? = null,
    val dateCompleted: String? = null,
    val dateExpiry: String? = null,
    val dateIssued: String? = null,
    val dateDueReference: String? = null,
    val reviewNotes: String? = null,
    val recurrent: Boolean? = true,
    val referenceId: String? = null,
    val notes: String? = null,
    override var cusvals: MutableList<CustomValue> = mutableListOf(),
    override var subcategoryCusvals: MutableList<CustomValue> = mutableListOf(),
    override var signatures: MutableList<Signature> = mutableListOf(),
    override var attachments: MutableList<Attachment> = mutableListOf(),
    override var pendingActions: MutableList<PendingActionPL> = mutableListOf()
) : BaseTask, IReviewPlanComponent, IPendingAction
