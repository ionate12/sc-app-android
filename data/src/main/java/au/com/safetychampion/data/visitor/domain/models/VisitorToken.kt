package au.com.safetychampion.data.visitor.domain.models

data class VisitorToken(
    val pinRequired: Boolean?,
    val token: String?
)
