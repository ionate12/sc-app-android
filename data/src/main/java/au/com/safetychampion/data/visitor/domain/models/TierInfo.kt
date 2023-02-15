package au.com.safetychampion.data.visitor.domain.models

data class TierInfo(
    val _id: String,
    val type: String,
    val name: String,
    val VISIT_TERMS: VisitTerm,
    val workplace: TierInfo?
)
