package au.com.safetychampion.data.domain.models

interface SCBaseMessage {
    val type: String
    val value: Any
}
