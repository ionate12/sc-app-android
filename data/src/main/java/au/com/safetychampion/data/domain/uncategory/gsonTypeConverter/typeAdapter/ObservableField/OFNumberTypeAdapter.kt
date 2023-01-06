package au.com.safetychampion.data.domain.uncategory.gsonTypeConverter.typeAdapter.ObservableField

import androidx.databinding.ObservableInt
import com.google.gson.* // ktlint-disable no-wildcard-imports
import java.lang.reflect.Type

class OFNumberTypeAdapter : JsonDeserializer<ObservableInt>, JsonSerializer<ObservableInt> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): ObservableInt {
        return ObservableInt(json?.asInt ?: -1)
    }

    override fun serialize(src: ObservableInt?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        return JsonPrimitive(src!!.get())
    }
}
