package au.com.safetychampion.data.domain.models.action

import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.payload.ActionPojo
import com.google.gson.Gson
import com.google.gson.JsonElement

data class ActionSignOffPL(
    val sessionId: String,
    var _id: String,
    val moduleId: String,
    var body: ActionPojo? = null,
    val task: ActionTaskPojo
) : BasePL() {

    override fun toJsonElement(gson: Gson): JsonElement {
        body?.removeNullValueInCustomValues()
        return super.toJsonElement(gson)
    }
}
