package au.com.safetychampion.data.domain.models.noticeboard

import au.com.safetychampion.data.domain.models.AllocationOf
import au.com.safetychampion.data.domain.models.CreatedBy
import au.com.safetychampion.data.domain.models.Tier

data class Noticeboard(
    val category: String?,
    val categoryOther: String?,
    val control: Any?,
    val createdBy: CreatedBy?,
    val dateCreated: String?,
    val description: String?,
    val subcategory: String?,
    val subcategoryOther: String?,
    val tier: Tier?,
    val title: String?,
    val type: String?,
    val tzDateCreated: String?,
    val _id: String?,
    val allocationOf: AllocationOf? = null
) {
    override fun toString(): String {
        return "$category $categoryOther $$$$$$$$"
    }
}
