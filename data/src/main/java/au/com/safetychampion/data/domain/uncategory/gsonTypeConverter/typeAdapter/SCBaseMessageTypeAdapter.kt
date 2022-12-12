package au.com.safetychampion.data.domain.uncategory.gsonTypeConverter.typeAdapter

import au.com.safetychampion.data.domain.models.visitor.SCBaseMessage
import au.com.safetychampion.data.domain.models.visitor.SCQuillDeltaMessage
import au.com.safetychampion.data.domain.models.visitor.SCTextMessage
import au.com.safetychampion.data.domain.uncategory.GsonHelper
import au.com.safetychampion.data.domain.uncategory.getGson
import com.google.gson.* // ktlint-disable no-wildcard-imports
import java.lang.reflect.Type

class SCBaseMessageTypeAdapter : JsonDeserializer<SCBaseMessage>, JsonSerializer<SCBaseMessage> {
    private val gson by lazy { getGson() }

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): SCBaseMessage? {
        if (json == null || !json.isJsonObject) return null

        val jsonObj = json.asJsonObject
        val type = jsonObj.get("type")!!.asString
        jsonObj.get("value").let {
            if (it.isJsonPrimitive && it.asJsonPrimitive.isString) {
                return SCTextMessage(type, it.asString)
            } else if (it.isJsonObject) {
                return SCQuillDeltaMessage(type, it.asJsonObject)
            } else {
                return null
            }
        }
    }

    override fun serialize(
        src: SCBaseMessage?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return GsonHelper.CLEAN_INSTANCE.toJsonTree(src)
    }
}
