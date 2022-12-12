package au.com.safetychampion.scmobile.gsonTypeConverter.typeAdapter.ObservableField

import androidx.databinding.ObservableLong
import com.google.gson.* // ktlint-disable no-wildcard-imports
import java.lang.reflect.Type

class OFLongTypeAdapter : JsonSerializer<ObservableLong>, JsonDeserializer<ObservableLong> {

    override fun serialize(src: ObservableLong?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        return JsonPrimitive(src?.get())
    }

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): ObservableLong {
        return ObservableLong(json?.asLong ?: -1)
    }
}
