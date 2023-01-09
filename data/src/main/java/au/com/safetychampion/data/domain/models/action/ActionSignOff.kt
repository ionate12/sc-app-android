package au.com.safetychampion.data.domain.models.action

data class ActionSignOff(
    val body: ActionPojo?,
    val _id: String?,
    val moduleId: String?,
    val task: ActionTaskPojo?
)
