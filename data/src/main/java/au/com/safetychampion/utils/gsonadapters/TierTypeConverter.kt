package au.com.safetychampion.utils.gsonadapters // ktlint-disable filename

import au.com.safetychampion.data.domain.models.TierType
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type

class TierTypeConverter : JsonSerializer<TierType>, JsonDeserializer<TierType> {
    override fun serialize(
        src: TierType,
        typeOfSrc: Type,
        context: JsonSerializationContext
    ): JsonElement {
        return context.serialize(src.value)
    }

    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): TierType {
        return TierType.fromString(json.asString)
    }
}
