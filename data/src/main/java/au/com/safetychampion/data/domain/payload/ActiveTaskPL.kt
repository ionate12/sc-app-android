package au.com.safetychampion.data.domain.payload

import com.google.gson.JsonElement

data class ActiveTaskPL(
    private val filter: Modules? = null
) : BasePL() {

    override fun toJsonElement(): JsonElement {
        return if (filter == null) SCEmptyJsonPrimitive else super.toJsonElement()
    }

    data class Modules(val modules: List<String?>?)
}
