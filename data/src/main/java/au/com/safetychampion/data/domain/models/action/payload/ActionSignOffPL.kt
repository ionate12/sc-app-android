package au.com.safetychampion.data.domain.models.action.payload

import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.models.action.ActionTask
import com.google.gson.Gson
import com.google.gson.JsonElement

data class ActionSignOffPL(
    var _id: String?,
    val moduleId: String?,
    var body: ActionPL? = null,
    val task: ActionTask
) : BasePL() {

    override fun toJsonElement(gson: Gson): JsonElement {
        body?.removeNullValueInCustomValues()
        return super.toJsonElement(gson)
    }
}
