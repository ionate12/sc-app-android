package au.com.safetychampion.data.domain.base

import au.com.safetychampion.data.data.MultipartPLConverter
import com.google.gson.Gson
import com.google.gson.JsonElement
import okhttp3.MultipartBody

abstract class BasePL {
    companion object {
        fun empty() = object : BasePL() {}
    }
    open fun toJsonElement(gson: Gson): JsonElement {
        return gson.toJsonTree(this)
    }

    open suspend fun toMultiPartBody(): List<MultipartBody.Part> {
        return MultipartPLConverter().invoke(this)
    }
}
