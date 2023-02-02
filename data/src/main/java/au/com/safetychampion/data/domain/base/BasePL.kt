package au.com.safetychampion.data.domain.base

import au.com.safetychampion.data.domain.core.IForceNullValues
import au.com.safetychampion.data.domain.manager.IGsonManager
import au.com.safetychampion.data.domain.models.customvalues.CustomValue
import au.com.safetychampion.data.domain.models.customvalues.ICategoryCusval
import au.com.safetychampion.data.domain.models.customvalues.ICusval
import au.com.safetychampion.data.util.extension.koinGet
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonNull
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

abstract class BasePL {
    companion object {
        fun empty() = object : BasePL() {}
    }
    open fun toJsonElement(gson: Gson): JsonElement {
        return gson.toJsonTree(this)
    }

    open fun toRequestBody(): RequestBody {
        val gsonManager = koinGet<IGsonManager>()
        val jsonObject = gsonManager.gson.toJsonTree(this).asJsonObject

        // 1. Add Cusvals if needed
        (this as? ICusval)?.cusvals?.toPL(gsonManager.nullGson)?.let {
            jsonObject.add("cusvals", it)
        }

        // 2. Add catCusvals if needed
        (this as? ICategoryCusval)?.categoryCusvals?.toPL(gsonManager.nullGson)?.let {
            jsonObject.add("categoryCusvals", it)
        }

        // 3. Add forceNullsValue if needed
        (this as? IForceNullValues)?.forceNullKeys?.let { list ->
            list.forEach { jsonObject.add(it, JsonNull.INSTANCE) }
            jsonObject.remove("forceNullKeys")
        }

        return gsonManager.nullGson.toJson(jsonObject)
            .toRequestBody(
                "multipart/form-data"
                    .toMediaTypeOrNull()
            )
    }

    private fun List<CustomValue>.toPL(nullGson: Gson): JsonElement = map {
        it.toPayload()
    }.let {
        nullGson.toJsonTree(it)
    }
}
