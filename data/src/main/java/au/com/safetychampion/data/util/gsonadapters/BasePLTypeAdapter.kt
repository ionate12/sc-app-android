package au.com.safetychampion.data.util.gsonadapters

import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.manager.IGsonManager
import au.com.safetychampion.data.util.extension.koinGet
import com.google.gson.JsonElement
import com.google.gson.JsonNull
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type

class BasePLTypeAdapter : JsonSerializer<BasePL> {
    private val gson = koinGet<IGsonManager>().gson
    override fun serialize(
        src: BasePL?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?,
    ): JsonElement {
        return src?.toJsonElement(gson) ?: JsonNull.INSTANCE
    }
}
