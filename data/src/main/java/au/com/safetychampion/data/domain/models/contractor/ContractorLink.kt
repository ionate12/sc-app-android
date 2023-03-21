package au.com.safetychampion.data.domain.models.contractor

import au.com.safetychampion.data.domain.models.Tier
import au.com.safetychampion.data.domain.models.customvalues.CusvalPojo
import au.com.safetychampion.data.domain.models.workplace.CreatedBy
import au.com.safetychampion.data.domain.uncategory.DocAttachment

open class ContractorLink(
    var id: String? = null,
    var type: String? = null,
    var tier: Tier? = null,
    var name: String? = null,
    var description: String? = null,
    var frequencyKey: String? = null,
    var frequencyValue: Long? = null,
    var createdBy: CreatedBy? = null,
    var tzDateCreated: String? = null,
    var dateCreated: String? = null,
    var archived: Boolean = false,
    var referenceId: String? = null,
    var category: String? = null,
    var categoryOther: String? = null,
    var subcategory: String? = null,
    var subcategoryOther: String? = null,
    var holder: String? = null,
    var holderOther: String? = null,
    var cusvals: List<CusvalPojo>? = null,
    var attachments: List<DocAttachment>? = null,
    var dateIssued: String? = null,
    var dateExpiry: String? = null,
    var notes: String? = null
) {
    // TODO: Extends UniversalCell()?
}
