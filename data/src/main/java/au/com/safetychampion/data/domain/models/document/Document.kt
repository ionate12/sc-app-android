package au.com.safetychampion.data.domain.models.document

import au.com.safetychampion.data.domain.models.Tier
import au.com.safetychampion.data.domain.models.workplace.AllocationOf
import au.com.safetychampion.data.domain.uncategory.Queryable
import java.util.*

class Document(
    var copyOf: Tier? = null,
    var review: DocumentReview? = null,
    var shareWithWorkplace: Boolean = false,
    var tier: Tier? = null,
    var type: String? = null,
    var version: DocumentVersion? = null,
    var _id: String? = null,
    var allocationOf: AllocationOf? = null,
    var allocationActive: Boolean = false,
    var copySource: DocumentCopySource? = null,
    val allocationList: MutableList<String> = mutableListOf()

) : Queryable {

    private fun toStringQuery(): String {
        val s =
            (
                version!!.name + version!!.category + version!!.categoryOther + version!!.description +
                    version!!.subcategory + version!!.subcategoryOther + version!!.dateIssued + tier!!.type
                )
        return s.trim { it <= ' ' }.lowercase(Locale.getDefault())
    }

    override fun containsQueryString(query: String): Boolean {
        return toStringQuery().contains(query.lowercase(Locale.getDefault()))
    }

    override fun sortKey(): String {
        return version!!.name!!
    }
}
