package au.com.safetychampion.data.domain.models.action

data class ActionSignOffPojo(
    var sessionId: String? = null,
    var _id: String? = null,
    var moduleId: String? = null,
    var body: ActionBody? = null,
    var task: ActionTaskPojo? = null,
    var errorMessage: String? = null
)
