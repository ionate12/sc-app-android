package au.com.safetychampion.data.domain.models

import com.google.gson.JsonObject

data class SignOffPojo(
    var taskId: String? = null,
    var moduleId: String? = null,
    var body: JsonObject? = null,
    var task: JsonObject? = null,
    var sessionId: String? = null
)
