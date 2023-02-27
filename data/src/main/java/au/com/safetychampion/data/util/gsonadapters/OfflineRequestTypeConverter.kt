package au.com.safetychampion.data.util.gsonadapters // ktlint-disable filename

import au.com.safetychampion.data.data.common.OfflineRequest
import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.manager.IGsonManager
import au.com.safetychampion.data.domain.models.inspections.payload.InspectionNewChildPL
import au.com.safetychampion.data.domain.models.inspections.payload.InspectionTaskStartPL
import au.com.safetychampion.data.util.extension.koinGet
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type

class OfflineRequestTypeConverter : JsonSerializer<OfflineRequest>, JsonDeserializer<OfflineRequest> {
    private val gson by lazy { koinGet<IGsonManager>().cleanGson }
    override fun serialize(
        src: OfflineRequest,
        typeOfSrc: Type,
        context: JsonSerializationContext
    ): JsonElement = gson.toJsonTree(src)

    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext
    ): OfflineRequest? {
        val type = json.asJsonObject["type"]?.let {
            OfflineRequest.Type.valueOf(it.asString)
        } ?: return null
        val pl: BasePL = when (type) {
            OfflineRequest.Type.INSP_NEW_CHILD_START -> context.deserialize(json.asJsonObject["pl"], InspectionNewChildPL::class.java)
            OfflineRequest.Type.INSP_START -> context.deserialize(json.asJsonObject["pl"], InspectionTaskStartPL::class.java)
        }
        return OfflineRequest(type, pl)
    }
}
