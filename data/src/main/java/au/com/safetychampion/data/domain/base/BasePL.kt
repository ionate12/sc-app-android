package au.com.safetychampion.data.domain.base

import au.com.safetychampion.data.domain.models.customvalues.CustomValue
import au.com.safetychampion.data.util.extension.isNullOrEmpty
import au.com.safetychampion.data.util.extension.toJsonString
import com.google.gson.Gson
import com.google.gson.JsonElement
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

abstract class BasePL {
    open fun toJsonElement(gson: Gson): JsonElement {
        return gson.toJsonTree(this)
    }

    open fun toRequestBody(): RequestBody {
        return this
            .toJsonString()!!
            .toRequestBody(
                "multipart/form-data"
                    .toMediaTypeOrNull()
            )
    }
}

abstract class CustomValuePL : BasePL() {

    abstract val categoryCusvals: MutableList<CustomValue>?
    abstract val cusvals: MutableList<CustomValue>?

    fun removeNullValueInCustomValues() {
        categoryCusvals?.removeIf { it.value.isNullOrEmpty() }
        cusvals?.removeIf { it.value.isNullOrEmpty() }
    }

    override fun toJsonElement(gson: Gson): JsonElement {
        removeNullValueInCustomValues()
        return super.toJsonElement(gson)
    }
}
