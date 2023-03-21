package au.com.safetychampion.data.domain.models.reviewplan

import au.com.safetychampion.data.domain.Attachment
import au.com.safetychampion.data.domain.base.BaseModule
import au.com.safetychampion.data.domain.models.AllocationOf
import au.com.safetychampion.data.domain.models.CreatedBy
import au.com.safetychampion.data.domain.models.SCHolderLink
import au.com.safetychampion.data.domain.models.Tier
import au.com.safetychampion.data.domain.models.action.ActionLink
import au.com.safetychampion.data.domain.models.customvalues.CustomValue
import au.com.safetychampion.data.domain.models.customvalues.CusvalPojo
import au.com.safetychampion.data.domain.models.inspections.InspectionTemplateMeta

data class ReviewPlanReview(
    val actionLinks: List<ActionLink> = listOf(),
    val allocationActive: Boolean = false,
    val allocationOf: AllocationOf? = null,
    val attachments: List<Attachment> = listOf(),
    val category: String? = null,
    val categoryOther: String? = null,
    val control: InspectionTemplateMeta.Control? = null,
    val createdBy: CreatedBy? = null,
    val cusvals: List<CustomValue> = listOf(),
    val dateCreated: String? = null,
    val dateExpiry: String? = null,
    val dateIssued: String? = null,
    val holder: String? = null,
    val holderLinks: List<SCHolderLink> = listOf(),
    val holderOther: String? = null,
    val notes: String? = null,
    val referenceId: String? = null,
    val subcategory: String? = null,
    val subcategoryOther: String? = null,
    val subcategoryCusvals: List<CusvalPojo> = listOf(),
    val task: ReviewPlanTask? = null,
    val tier: Tier? = null,
    override val type: String,
    val tzDateCreated: String? = null,
    override val _id: String
) : BaseModule
