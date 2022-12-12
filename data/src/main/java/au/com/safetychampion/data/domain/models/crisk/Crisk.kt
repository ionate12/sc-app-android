package au.com.safetychampion.data.domain.models.crisk

import au.com.safetychampion.data.domain.InspectionTemplateMeta
import au.com.safetychampion.data.domain.models.AllocationOf
import au.com.safetychampion.data.domain.models.CreatedBy
import au.com.safetychampion.data.domain.models.SCHolderLink
import au.com.safetychampion.data.domain.models.Tier
import au.com.safetychampion.data.domain.models.action.ActionLink
import au.com.safetychampion.data.domain.models.customvalues.CusvalPojo
import au.com.safetychampion.data.domain.uncategory.DocAttachment

data class Crisk(
    val _id: String,
    val type: String, // core.module.crisk
    val tier: Tier,
    val allocationActive: Boolean = false,
    val allocationOf: AllocationOf? = null,
    val referenceId: String? = null,
    val category: String,
    val categoryOther: String? = null,
    val subcategory: String,
    val subcategoryOther: String? = null,
    val title: String,
    val inherentRiskRating: String,
    val inherentRiskRatingOther: String? = null,
    val futureRiskRating: String? = null,
    val futureRiskRatingOther: String? = null,
    val currentMitigation: String,
    val futureMitigation: String? = null,
    val residualRisk: String,
    val residualRiskOther: String? = null,
    val futureControl: Boolean = false,
    val riskOwner: String,
    val riskOwnerOther: String? = null,
    val riskOwnerLinks: List<SCHolderLink>,
    var cusvals: List<CusvalPojo> = listOf(),
    var subcategoryCusvals: List<CusvalPojo> = listOf(),
    val notes: String,
    var attachments: List<DocAttachment>? = mutableListOf(),
    var actionLinks: MutableList<ActionLink>? = mutableListOf(),
    val dateIssued: String,
    val dateExpiry: String? = null,
    val createdBy: CreatedBy,
    val tzDateCreated: String,
    val dateCreated: String,
    val control: InspectionTemplateMeta.Control?,
    val archivalDetails: CriskSignoffPayload.ArchivalDetails? = null,
    val confidential: Boolean = false,
    val task: CriskTask? = null
)
