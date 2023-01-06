package au.com.safetychampion.data.domain.models.visitor

data class VisitorQRCodeData(
    val siteId: String? = null,
    val requiredPin: Boolean = false
)
