package au.com.safetychampion.data.domain.models.crisk

import au.com.safetychampion.data.domain.* // ktlint-disable no-wildcard-imports
import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.core.Signature
import au.com.safetychampion.data.domain.models.CreatedBy
import au.com.safetychampion.data.domain.models.ForTask
import au.com.safetychampion.data.domain.models.IAttachment
import au.com.safetychampion.data.domain.models.ICusval
import au.com.safetychampion.data.domain.models.IForceNullValues
import au.com.safetychampion.data.domain.models.IPendingActionPL
import au.com.safetychampion.data.domain.models.ISignature
import au.com.safetychampion.data.domain.models.ISubcategoryCusval
import au.com.safetychampion.data.domain.models.Tier
import au.com.safetychampion.data.domain.models.action.ActionLink
import au.com.safetychampion.data.domain.models.action.network.PendingActionPL
import au.com.safetychampion.data.domain.models.customvalues.CustomValue
import au.com.safetychampion.data.domain.models.task.Task
import com.google.gson.annotations.SerializedName

interface ICriskTaskPLComponents :
    ICusval, ISubcategoryCusval, IAttachment, ISignature, IPendingActionPL, IForceNullValues {
    override val forceNullKeys: List<String> get() = listOf(
        "futureRiskRating",
        "futureRiskRatingOther",
        "futureMitigation"
    )
}

data class CriskTaskPL(
    val _id: String? = null,
    val type: String? = null,
    val tier: Tier? = null,
    val allocationActive: Boolean? = null,
    val archivalDetails: ArchivalDetails? = null,
    val referenceID: String? = null,
    val category: String? = null,
    val subcategory: String? = null,
    val subcategoryOther: String? = null,
    val title: String,
    val inherentRiskRating: String,
    val inherentRiskRatingOther: String? = null,
    val futureRiskRating: String? = null,
    val futureRiskRatingOther: String? = null,
    val currentMitigation: String? = null,
    val futureMitigation: String? = null,
    val residualRisk: String? = null,
    val residualRiskOther: String? = null,
    val futureControl: Boolean? = null,
    var riskOwner: String? = null,
    val riskOwnerOther: String? = null,
    val riskOwnerLinks: List<CriskOwnerLink>? = null,
    val notes: String? = null,
    val actionLinks: List<ActionLink>? = null,
    val dateIssued: String? = null,
    val dateExpiry: String? = null,
    val createdBy: CreatedBy? = null,
    val tzDateCreated: String? = null,
    val dateCreated: String? = null,
    val control: InspectionTemplateMeta.Control? = null,
    val task: Task? = null,
    @SerializedName("for")
    val _for: ForTask? = null,
    val complete: Boolean? = null,
    val dateDue: String? = null,
    val description: String? = null,
    val reviewNotes: String? = null,
    val dateCompleted: String? = null,
    val recurrent: Boolean? = null,
    val tzDateSignedoff: String? = null,
    override var attachments: MutableList<Attachment>?,
    override var signatures: MutableList<Signature>,
    override var cusvals: MutableList<CustomValue>,
    override var subcategoryCusvals: MutableList<CustomValue>,
    override var pendingActions: MutableList<PendingActionPL>
) : BasePL(), ICriskTaskPLComponents {

    companion object {
        fun fromModel(model: CriskTask): CriskTaskPL {
            TODO("create criskTaskPL")
        }
    }

    override fun onPendingActionsCreated(links: List<ActionLink>): CriskTaskPL {
        return this.copy(actionLinks = (this.actionLinks ?: listOf()) + links)
    }

    data class ArchivalDetails(
        val notes: String? = null,
        val by: CreatedBy? = null,
        val tz: String? = null,
        val date: String? = null
    )
}
