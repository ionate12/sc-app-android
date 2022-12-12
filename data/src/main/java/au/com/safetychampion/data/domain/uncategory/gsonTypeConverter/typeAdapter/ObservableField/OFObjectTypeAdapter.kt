package au.com.safetychampion.data.domain.uncategory.gsonTypeConverter.typeAdapter.ObservableField

import androidx.databinding.ObservableField
import au.com.safetychampion.data.domain.uncategory.JsonConverter
import com.google.gson.* // ktlint-disable no-wildcard-imports
import java.lang.reflect.Type

class OFObjectTypeAdapter<T> :
    JsonSerializer<ObservableField<T>>,
    JsonDeserializer<ObservableField<T>> {
    override fun serialize(src: ObservableField<T>?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement? {
        return JsonConverter.toJsonTree(src?.get())
    }

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): ObservableField<T> {
        return ObservableField<T>(JsonConverter.fromJSON(json, typeOfT))
    }
}
