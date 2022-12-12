package au.com.safetychampion.scmobile.gsonTypeConverter.typeAdapter.ObservableField

import androidx.databinding.ObservableField
import com.google.gson.* // ktlint-disable no-wildcard-imports
import java.lang.reflect.Type

class OFStringTypeAdapter : JsonDeserializer<ObservableField<String>>, JsonSerializer<ObservableField<String>> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): ObservableField<String> {
        return ObservableField<String>(json?.asString ?: "")
    }

    override fun serialize(src: ObservableField<String>?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement? {
        if (src?.get() == null) {
            return null
        }
        return JsonPrimitive(src.get())
    }
}
