package au.com.safetychampion.data.domain.uncategory.gsonTypeConverter.typeAdapter

import au.com.safetychampion.data.domain.models.customvalues.CustomValue
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class CustomValueTypeAdapter : JsonDeserializer<CustomValue> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): CustomValue {
        return json?.asJsonObject?.let {
            CustomValue.fromJson(it)
        } ?: CustomValue()
    }
}
