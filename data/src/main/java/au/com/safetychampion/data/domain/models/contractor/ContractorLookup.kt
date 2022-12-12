package au.com.safetychampion.data.domain.models.contractor

import au.com.safetychampion.data.domain.models.SCHolderLink
import au.com.safetychampion.data.domain.uncategory.TaskType
import java.util.*

interface LookupItem {
    val _id: String?
    fun query(str: String): Boolean {
        return queryString().contains(str.toLowerCase().trim())
    }
    fun queryString(): String

    fun toSCHolderLink(): SCHolderLink
}

data class ContractorLookup(
    override val _id: String? = null,
    val tierId: String? = null,
    val businessName: String? = null,
    val category: String? = null,
    val categoryOther: String? = null,
    val subcategory: String? = null,
    val subcategoryOther: String? = null,
    val contacts: List<ContactLookUp?>? = null
) : LookupItem {

    var isHeader: Boolean = false

    private val contactString = contacts?.fold("") { str, item ->
        "$str${item?.name}${item?.emails?.joinToString("")}"
    }?.replace("null", "")

    val selectedContact: Pair<String?, String?>? by lazy {
        if (contacts == null) return@lazy null
        for (it in contacts) {
            if (it?.name != null) {
                val email = it.emails?.mapNotNull { it }?.firstOrNull()
                if (email != null) {
                    return@lazy it.name to email
                }
            }
        }
        null
    }

    override fun queryString(): String {
        return "$businessName$category$categoryOther$subcategory$subcategoryOther$contactString"
            .replace("null", "").trim()
            .toLowerCase(Locale.getDefault())
    }

    override fun toSCHolderLink() = SCHolderLink(
        businessName = businessName,
        email = null,
        name = null,
        type = TaskType.CONTRACTOR,
        _id = _id,
        contactEmail = selectedContact?.second,
        contactName = selectedContact?.first
    )

    data class ContactLookUp(
        val name: String? = null,
        val position: String? = null,
        val emails: List<String?>? = null
    )
    companion object {
        fun fromRawData(data: List<Any?>): ContractorLookup = with(data) {
            return@with ContractorLookup(
                _id = this.getOrNull(0) as? String,
                tierId = this.getOrNull(1) as? String,
                businessName = this.getOrNull(2) as? String,
                category = this.getOrNull(3) as? String,
                categoryOther = this.getOrNull(4) as? String,
                subcategory = this.getOrNull(5) as? String,
                subcategoryOther = this.getOrNull(6) as? String,
                contacts = getContactLookUp(this.getOrNull(7) as? List<Any?>)
            )
        }

        /**
         * [
         [
         "Gerald DuBuque",
         "Contract Manager",
         [
         "Abdullah26@yahoo.com",
         "Filiberto96@gmail.com",
         "Salvatore.Turcotte@yahoo.com"
         ]
         ]
         ]
         */
        private fun getContactLookUp(data: List<Any?>?): List<ContactLookUp?>? {
            if (data == null) return null
            return data.map { outList ->
                val dataList = (outList as? List<Any?>) ?: return@map null
                ContactLookUp(
                    name = dataList.getOrNull(0) as? String,
                    position = dataList.getOrNull(1) as? String,
                    emails = (dataList.getOrNull(2) as? List<String?>?)
                )
            }
        }
    }
}
