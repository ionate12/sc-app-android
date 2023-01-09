package au.com.safetychampion.data.domain.models.action.payload

import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.models.action.ActionSignOffForm
import com.google.gson.Gson
import com.google.gson.JsonElement

data class ActionSignOff(
    var _id: String?,
    val moduleId: String?,
    var body: Action? = null,
    val task: ActionSignOffForm
) : BasePL() {

    override fun toJsonElement(gson: Gson): JsonElement {
        body?.removeNullValueInCustomValues()
        return super.toJsonElement(gson)
    }
}
