package au.com.safetychampion.data.domain.models.contractor

import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.models.* // ktlint-disable no-wildcard-imports
import au.com.safetychampion.data.domain.uncategory.DocAttachment

data class ContractorProfile(
    var _id: String? = null,
    var archived: Boolean = false,
    var type: String? = null,
    var tier: Tier? = null,
    var allocationActive: Boolean = false,
    var allocationOf: AllocationOf? = null,
    var links: List<ContractorLink>? = listOf(),
    var businessName: String? = null,
    var category: String? = null,
    var categoryOther: String? = null,
    var subcategory: String? = null,
    var subcategoryOther: String? = null,
    var status: String? = null,
    var referenceIdType: String? = null,
    var referenceId: String? = null,
    var attachments: MutableList<DocAttachment> = mutableListOf(),
    var createdBy: CreatedBy? = null,
    var tzDateCreated: String? = null,
    var dateCreated: String? = null,
    var businessNotes: String? = null,
    var statusOther: String? = null,
    var address: Address? = null,
    var locations: MutableList<Location>? = null,
    var contacts: MutableList<Contact>? = null,
    @Transient
    var fromTaskType: String? = null
) : BasePL()
