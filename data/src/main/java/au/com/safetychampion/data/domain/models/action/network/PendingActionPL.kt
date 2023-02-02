package au.com.safetychampion.data.domain.models.action.network

import au.com.safetychampion.data.domain.Attachment

data class PendingActionPL(
    val action: ActionPL,
    val attachments: List<Attachment>
)
