package au.com.safetychampion.data.domain.uncategory.gsonTypeConverter.typeAdapter

import au.com.safetychampion.data.domain.payload.BasePL
import com.google.gson.JsonElement
import com.google.gson.JsonNull
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type

class BasePLTypeAdapter : JsonSerializer<BasePL> {
    override fun serialize(
        src: BasePL?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return src?.toJsonElement() ?: JsonNull.INSTANCE
    }
}
