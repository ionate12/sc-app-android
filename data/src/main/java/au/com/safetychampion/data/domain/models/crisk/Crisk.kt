package au.com.safetychampion.data.domain.models.crisk

import au.com.safetychampion.data.domain.InspectionTemplateMeta
import au.com.safetychampion.data.domain.base.BaseModule
import au.com.safetychampion.data.domain.models.CreatedBy
import au.com.safetychampion.data.domain.models.SCHolderLink
import au.com.safetychampion.data.domain.models.Tier
import au.com.safetychampion.data.domain.models.action.ActionLink
import au.com.safetychampion.data.domain.models.customvalues.CustomValue
import au.com.safetychampion.data.domain.models.customvalues.ICusval
import au.com.safetychampion.data.domain.uncategory.DocAttachment

data class Crisk(
    // criskTask._for {
    val allocationActive: Boolean? = null,
    val archived: Boolean? = null,
    val confidential: Boolean? = null,
    val futureControl: Boolean? = null,
    override val _id: String?,
    override val type: String?,
    // }
    val actionLinks: MutableList<ActionLink> = mutableListOf(),
    val attachments: MutableList<DocAttachment> = mutableListOf(),
    val category: String? = null,
    val control: InspectionTemplateMeta.Control? = null,
    val createdBy: CreatedBy? = null,
    val currentMitigation: String? = null,
    val dateCreated: String? = null,
    val dateExpiry: String? = null,
    val dateIssued: String? = null,
    val inherentRiskRating: String? = null,
    val referenceId: String? = null,
    val residualRisk: String? = null,
    val riskOwner: String? = null,
    val riskOwnerLinks: MutableList<SCHolderLink>? = mutableListOf(),
    val riskOwnerOther: String? = null,
    val subcategory: String? = null,
    val subcategoryCusvals: MutableList<CustomValue>? = null,
    val task: CriskTask? = null,
    val tier: Tier? = null,
    val title: String? = null,
    val tzDateCreated: String? = null,
    override var cusvals: MutableList<CustomValue>
) : BaseModule, ICusval
