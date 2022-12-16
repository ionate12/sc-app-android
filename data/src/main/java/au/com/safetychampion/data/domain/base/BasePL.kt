package au.com.safetychampion.data.domain.base

import au.com.safetychampion.data.gsonadapter.gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject

abstract class BasePL {
    companion object {
        fun createJsonObject(jsonString: String): JsonObject? {
            return try {
                gson().fromJson(jsonString, JsonObject::class.java)
            } catch (e: Exception) {
                null
            }
        }
    }
    open fun toJsonElement(): JsonElement {
        val gson = gson()
        return gson.toJsonTree(this)
    }
}
