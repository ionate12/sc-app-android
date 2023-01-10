package au.com.safetychampion.data.domain.models.action.payload

import au.com.safetychampion.data.domain.Attachment

data class PendingActionPL(
    val action: ActionPL,
    val attachment: List<Attachment>
)
