package au.com.safetychampion.data.domain.uncategory.gsonTypeConverter.typeAdapter

import au.com.safetychampion.data.domain.models.customvalues.CustomValueOption
import au.com.safetychampion.data.domain.uncategory.GsonHelper
import au.com.safetychampion.data.domain.uncategory.JsonConverter
import au.com.safetychampion.data.domain.uncategory.JsonUtils
import com.google.gson.* // ktlint-disable no-wildcard-imports
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
                rs.icon = JsonUtils.getStringProperty(obj, "icon")
                rs.name = JsonUtils.getStringProperty(obj, "name")
                rs.static = JsonUtils.getStringProperty(obj, "static")
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
        if (!src.options.isNullOrEmpty()) {
            var jsonSrc = GsonHelper.CLEAN_INSTANCE.toJsonTree(src).asJsonObject
            jsonSrc.add("options", JsonConverter.toJsonTree(src.options))
            return jsonSrc
        }
        return GsonHelper.CLEAN_INSTANCE.toJsonTree(src)
    }
}
