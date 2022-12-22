package au.com.safetychampion.data.domain.base

import com.google.gson.Gson
import com.google.gson.JsonElement

abstract class BasePL {
    open fun toJsonElement(gson: Gson): JsonElement {
        return gson.toJsonTree(this)
    }
}
