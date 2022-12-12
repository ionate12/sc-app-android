package au.com.safetychampion.data.domain.models.visitor

interface SCBaseMessage {
    val type: String
    val value: Any
}
