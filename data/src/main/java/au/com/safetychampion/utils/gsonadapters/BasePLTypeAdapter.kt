package au.com.safetychampion.utils.gsonadapters

import au.com.safetychampion.data.di.retrofit.IGson
import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.utils.getKoinInstance
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
        return src?.toJsonElement(
            gson = getKoinInstance<IGson>().cleanGson
        ) ?: JsonNull.INSTANCE
    }
}
