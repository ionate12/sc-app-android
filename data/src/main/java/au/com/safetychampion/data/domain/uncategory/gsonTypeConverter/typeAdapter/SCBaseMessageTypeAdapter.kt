package au.com.safetychampion.data.domain.uncategory.gsonTypeConverter.typeAdapter

import au.com.safetychampion.data.domain.manager.IGsonManager
import au.com.safetychampion.data.domain.models.SCBaseMessage
import au.com.safetychampion.data.domain.models.SCQuillDeltaMessage
import au.com.safetychampion.data.util.extension.koinInject
import au.com.safetychampion.data.visitor.domain.models.SCTextMessage
import com.google.gson.*
import java.lang.reflect.Type

class SCBaseMessageTypeAdapter : JsonDeserializer<SCBaseMessage>, JsonSerializer<SCBaseMessage> {
    private val gson by koinInject<IGsonManager>()

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
        return gson.cleanGson.toJsonTree(src)
    }
}
