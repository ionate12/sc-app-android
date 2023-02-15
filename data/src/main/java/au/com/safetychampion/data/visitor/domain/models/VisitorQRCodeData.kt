package au.com.safetychampion.data.visitor.domain.models

data class VisitorQRCodeData(
    val siteId: String? = null,
    val requiredPin: Boolean = false
)
