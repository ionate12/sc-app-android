package au.com.safetychampion.scmobile.gsonTypeConverter.typeAdapter.ObservableField

import au.com.safetychampion.data.domain.uncategory.ObservableNullableBoolean
import com.google.gson.* // ktlint-disable no-wildcard-imports
import java.lang.reflect.Type

class OFNullableBooleanTypeAdapter : JsonDeserializer<ObservableNullableBoolean>, JsonSerializer<ObservableNullableBoolean> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): ObservableNullableBoolean {
        val bool = ObservableNullableBoolean()
        bool.setFromBoolean(json?.asBoolean)
        return bool
    }

    override fun serialize(src: ObservableNullableBoolean?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        return JsonPrimitive(src?.asBoolean)
    }
}
