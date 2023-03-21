package au.com.safetychampion.data.domain.models.action.network

data class PendingActionPL(
    val action: ActionNewPL,
    val refId: String? = null
)
