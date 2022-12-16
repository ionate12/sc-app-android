package au.com.safetychampion.data.domain.payload

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive

open class BasePL {
    companion object {
        val SCEmptyJsonPrimitive = JsonPrimitive("{}")
    }
    open fun toJsonElement(): JsonElement {
        val gson = Gson()
        return gson.toJsonTree(this)
    }
}
