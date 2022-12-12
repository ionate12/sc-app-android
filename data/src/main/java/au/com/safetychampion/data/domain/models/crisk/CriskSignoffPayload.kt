package au.com.safetychampion.data.domain.models.crisk

import au.com.safetychampion.data.domain.* // ktlint-disable no-wildcard-imports
import au.com.safetychampion.data.domain.models.CreatedBy
import au.com.safetychampion.data.domain.models.ForTask
import au.com.safetychampion.data.domain.models.Task
import au.com.safetychampion.data.domain.models.Tier
import au.com.safetychampion.data.domain.models.action.ActionLink
import au.com.safetychampion.data.domain.models.customvalues.CusvalPojo
import au.com.safetychampion.data.domain.uncategory.DocAttachment
import com.google.gson.annotations.SerializedName

data class CriskSignoffPayload(
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
    val cusvals: List<CusvalPojo>? = null,
    val subcategoryCusvals: List<CusvalPojo>? = null,
    val notes: String? = null,
    val attachments: List<DocAttachment>? = null,
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
    val signatures: List<InspectionFormPayload.SignaturePayload>? = null,
    val recurrent: Boolean? = null,
    val tzDateSignedoff: String? = null
) {
    data class ArchivalDetails(
        val notes: String? = null,
        val by: CreatedBy? = null,
        val tz: String? = null,
        val date: String? = null
    )

    companion object {
        val forceNullValues = listOf(
            "futureRiskRating",
            "futureRiskRatingOther",
            "futureMitigation"
        )
    }
}
