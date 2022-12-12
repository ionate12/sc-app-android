package au.com.safetychampion.data.domain.models.reviewplan

import au.com.safetychampion.data.domain.* // ktlint-disable no-wildcard-imports
import au.com.safetychampion.data.domain.models.AllocationOf
import au.com.safetychampion.data.domain.models.CreatedBy
import au.com.safetychampion.data.domain.models.SCHolderLink
import au.com.safetychampion.data.domain.models.Tier
import au.com.safetychampion.data.domain.models.action.ActionLink
import au.com.safetychampion.data.domain.models.customvalues.CusvalPojo
import au.com.safetychampion.data.domain.uncategory.DocAttachment
import java.util.*

data class ReviewPlanReview(
    var _id: String? = null,
    var type: String? = null,
    var tier: Tier? = null,
    var referenceId: String? = null,
    var category: String? = null,
    var categoryOther: String? = null,
    var subcategory: String? = null,
    var subcategoryOther: String? = null,
    var holder: String? = null,
    val holderLinks: List<SCHolderLink> = mutableListOf(),
    var holderOther: String? = null,
    var control: InspectionTemplateMeta.Control? = null,
    var cusvals: List<CusvalPojo> = listOf(),
    var subcategoryCusvals: List<CusvalPojo> = listOf(),
    var attachments: List<DocAttachment> = mutableListOf(),
    var dateIssued: String? = null,
    var dateExpiry: String? = null,
    var createdBy: CreatedBy? = null,
    var tzDateCreated: String? = null,
    var dateCreated: String? = null,
    var notes: String? = null,
    var allocationActive: Boolean = false,
    var allocationOf: AllocationOf? = null,
    var actionLinks: MutableList<ActionLink>? = null
) {

    @delegate:Transient
    val allCusvals by lazy { getAllCusval() }

    private fun getAllCusval(): List<CusvalPojo> {
        return ArrayList<CusvalPojo>().apply {
            addAll(cusvals)
            addAll(subcategoryCusvals)
        }.toList()
    }
}
