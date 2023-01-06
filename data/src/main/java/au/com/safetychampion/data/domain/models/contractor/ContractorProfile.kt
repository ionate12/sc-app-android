package au.com.safetychampion.data.domain.models.contractor

import androidx.databinding.ObservableField
import au.com.safetychampion.data.domain.models.* // ktlint-disable no-wildcard-imports
import au.com.safetychampion.data.domain.uncategory.DocAttachment

data class ContractorProfile(
    var _id: String? = null,
    var archived: Boolean = false,
    var type: ObservableField<String> = ObservableField(),
    var tier: Tier? = null,
    var allocationActive: Boolean = false,
    var allocationOf: AllocationOf? = null,
    var links: List<ContractorLink>? = listOf(),
    var businessName: ObservableField<String> = ObservableField(),
    var category: ObservableField<String> = ObservableField(),
    var categoryOther: String? = null,
    var subcategory: ObservableField<String> = ObservableField(),
    var subcategoryOther: ObservableField<String> = ObservableField(),
    var status: ObservableField<String> = ObservableField(),
    var referenceIdType: ObservableField<String> = ObservableField(),
    var referenceId: ObservableField<String> = ObservableField(),
    var attachments: MutableList<DocAttachment> = mutableListOf(),
    var createdBy: CreatedBy? = null,
    var tzDateCreated: ObservableField<String> = ObservableField(),
    var dateCreated: ObservableField<String> = ObservableField(),
    var businessNotes: ObservableField<String> = ObservableField(),
    var statusOther: String? = null,
    var address: Address? = null,
    var locations: MutableList<Location>? = null,
    var contacts: MutableList<Contact>? = null,
    @Transient
    var fromTaskType: String? = null
)
