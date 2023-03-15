package au.com.safetychampion.data.domain.uncategory.gsonTypeConverter.typeAdapter

import au.com.safetychampion.data.domain.Attachment
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class AttachmentTypeAdapter : JsonDeserializer<Attachment> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Attachment? {
        if (json == null || !json.isJsonObject) {
            return null
        }
        val typeToken: TypeToken<out Attachment> = with(json.asJsonObject) {
            when {
                has("uri") -> object : TypeToken<Attachment.New>() {}
                else -> object : TypeToken<Attachment.Review>() {}
            }
        }
        return context!!.deserialize(json, typeToken.type)
    }
}
