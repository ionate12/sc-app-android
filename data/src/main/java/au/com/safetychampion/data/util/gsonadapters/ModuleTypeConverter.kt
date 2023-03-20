package au.com.safetychampion.data.util.gsonadapters // ktlint-disable filename

import au.com.safetychampion.data.domain.core.ModuleType
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type

class ModuleTypeConverter : JsonSerializer<ModuleType>, JsonDeserializer<ModuleType> {

    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): ModuleType {
        return ModuleType.fromString(json.asString)
    }

    override fun serialize(
        src: ModuleType,
        typeOfSrc: Type,
        context: JsonSerializationContext
    ): JsonElement {
        return context.serialize(src.title)
    }
}
