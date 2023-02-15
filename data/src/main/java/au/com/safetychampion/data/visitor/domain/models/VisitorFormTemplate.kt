package au.com.safetychampion.data.visitor.domain.models

data class VisitorFormTemplate(
    val role: VisitorRole,
    val arrive: VisitorParentForm?,
    val leave: VisitorParentForm?
)
