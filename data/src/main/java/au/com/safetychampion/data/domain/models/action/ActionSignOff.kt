package au.com.safetychampion.data.domain.models.action

import au.com.safetychampion.data.domain.payload.ActionPojo

data class ActionSignOff(
    val body: ActionPojo?,
    val _id: String?,
    val moduleId: String?,
    val task: ActionTaskPojo?
)
