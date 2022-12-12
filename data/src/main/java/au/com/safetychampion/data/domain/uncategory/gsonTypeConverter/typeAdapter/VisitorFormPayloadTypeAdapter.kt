package au.com.safetychampion.data.domain.uncategory.gsonTypeConverter.typeAdapter

import au.com.safetychampion.data.domain.models.visitor.VisitorPayload
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type

/**
 * Created by Minh Khoi MAI on 27/1/21.
 */
class VisitorFormPayloadTypeAdapter : JsonSerializer<VisitorPayload.Form> {

    override fun serialize(src: VisitorPayload.Form?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonObject? {
        val jsonObj = GsonBuilder()
            .serializeNulls()
            .create()
            .toJsonTree(src).asJsonObject
        return jsonObj
    }
}
