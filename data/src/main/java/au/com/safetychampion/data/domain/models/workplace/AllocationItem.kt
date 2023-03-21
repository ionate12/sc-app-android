package au.com.safetychampion.data.domain.models.workplace

data class AllocationItem(
    var allocated: Boolean = false,
    var type: String? = null,
    var _id: String? = null
)
