package au.com.safetychampion.data.domain.models.crisk

import au.com.safetychampion.data.domain.* // ktlint-disable no-wildcard-imports
import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.core.Signature
import au.com.safetychampion.data.domain.models.* // ktlint-disable no-wildcard-imports
import au.com.safetychampion.data.domain.models.action.ActionLink
import au.com.safetychampion.data.domain.models.action.network.PendingActionPL
import au.com.safetychampion.data.domain.models.customvalues.CustomValue
import au.com.safetychampion.data.domain.models.inspections.InspectionTemplateMeta
import au.com.safetychampion.data.domain.models.task.ForTask
import au.com.safetychampion.data.domain.models.task.Task
import au.com.safetychampion.data.domain.models.workplace.CreatedBy
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
    override var attachments: MutableList<Attachment>,
    override var signatures: MutableList<Signature>,
    override var cusvals: MutableList<CustomValue>,
    override var subcategoryCusvals: MutableList<CustomValue>,
    override var pendingActions: MutableList<PendingActionPL>
) : BasePL(), ICriskTaskPLComponents {

    companion object {
        fun fromModel(model: CriskTask): CriskTaskPL {
            return CriskTaskPL(
                _id = model._id,
                type = model.type,
                tier = model.tier,
                allocationActive = null,
                archivalDetails = null,
                referenceID = model.referenceId,
                category = null,
                subcategory = null,
                subcategoryOther = null,
                title = model.title ?: "",
                inherentRiskRating = model.inherentRiskRating ?: "",
                inherentRiskRatingOther = model.inherentRiskRatingOther,
                futureRiskRating = model.futureRiskRating,
                futureRiskRatingOther = model.futureRiskRatingOther,
                currentMitigation = model.currentMitigation ?: "",
                futureMitigation = model.futureMitigation,
                residualRisk = model.residualRisk,
                residualRiskOther = model.residualRiskOther,
                futureControl = model.futureControl,
                riskOwner = model.riskOwner,
                riskOwnerOther = model.riskOwnerOther,
                riskOwnerLinks = emptyList(),
                notes = model.notes,
                actionLinks = mutableListOf(),
                dateIssued = model.dateIssued,
                dateExpiry = model.dateExpiry,
                createdBy = null,
                tzDateCreated = null,
                dateCompleted = model.dateCompleted,
                control = null,
                dateCreated = null,
                task = null,
                _for = null,
                complete = model.complete,
                dateDue = model.dateDue,
                description = model.description,
                reviewNotes = model.reviewNotes,
                recurrent = model.recurrent,
                tzDateSignedoff = model.tzDateSignedoff,
                attachments = model.attachments,
                signatures = model.signatures,
                cusvals = model.cusvals,
                subcategoryCusvals = model.subcategoryCusvals,
                pendingActions = model.pendingActions
            )

            // TODO("Need to review again")
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
