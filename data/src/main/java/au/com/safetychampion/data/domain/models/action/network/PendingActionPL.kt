package au.com.safetychampion.data.domain.models.action.network

data class PendingActionPL(
    val action: ActionPL,
    val refId: String? = null
)
