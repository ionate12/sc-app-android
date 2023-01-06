package au.com.safetychampion.data.domain.uncategory.gsonTypeConverter.typeAdapter.ObservableField

import androidx.databinding.ObservableBoolean
import com.google.gson.* // ktlint-disable no-wildcard-imports
import java.lang.reflect.Type

class OFBooleanTypeAdapter :
    JsonDeserializer<ObservableBoolean>,
    JsonSerializer<ObservableBoolean> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): ObservableBoolean {
        return ObservableBoolean(json?.asBoolean!!)
    }

    override fun serialize(src: ObservableBoolean?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        return JsonPrimitive(src!!.get())
    }
}
