package au.com.safetychampion.data.domain.uncategory.gsonTypeConverter.typeAdapter

import au.com.safetychampion.data.domain.manager.IGsonManager
import au.com.safetychampion.data.domain.models.customvalues.CustomValueOption
import au.com.safetychampion.data.util.extension.asStringOrNull
import au.com.safetychampion.data.util.extension.jsonObjectOf
import au.com.safetychampion.data.util.extension.koinGet
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

/**
 * Created by @Minh_Khoi_MAI on 2019-12-04
 */
class CustomValueOptionTypeAdapter : JsonDeserializer<CustomValueOption>, JsonSerializer<CustomValueOption> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): CustomValueOption {
        if (json != null) {
            if (json.isJsonPrimitive) {
                if (json.asString == "SEPARATOR") {
                    var op = CustomValueOption()
                    op.isSeparator = true
                    return op
                } else {
                    val stringValue = json.asString
                    return CustomValueOption().apply { this.stringValue = stringValue }
                }
            } else {
                val rs = CustomValueOption()
                val obj = json.asJsonObject
                if (obj.has("options")) {
                    val options: List<CustomValueOption> = context!!.deserialize(json.asJsonObject["options"], object : TypeToken<List<CustomValueOption>>() {}.type)
                    rs.options = options
                }
                rs.icon = obj["icon"].asStringOrNull()
                rs.name = obj["name"].asStringOrNull()
                rs.static = obj["static"].asStringOrNull()
                rs.isSeparator = false
                return rs
            }
        }
        return CustomValueOption()
    }

    override fun serialize(src: CustomValueOption?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        if (src!!.isSeparator) {
            return JsonPrimitive("SEPARATOR")
        }
        if (src.stringValue != null) {
            return JsonPrimitive(src.stringValue)
        }
        // NOTICE: This prevent infinite loop
        val cleanGson = koinGet<IGsonManager>().cleanGson
        return if (!src.options.isNullOrEmpty()) {
            jsonObjectOf(src = src, customGson = cleanGson)?.apply {
                add("options", jsonObjectOf(src.options!!, null))
            }!!
        } else {
            cleanGson.toJsonTree(src)
        }
    }
}
