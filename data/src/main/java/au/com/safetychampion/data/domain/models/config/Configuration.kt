package au.com.safetychampion.data.domain.models.config

import au.com.safetychampion.data.domain.core.ModuleType
import au.com.safetychampion.data.domain.models.customvalues.CustomValue
import au.com.safetychampion.data.util.extension.asStringOrNull
import com.google.gson.JsonElement

typealias ConfigItemMap = Map<String, JsonElement>

data class Configuration(
    val type: ModuleType,
    private val values: List<ConfigurationItem>,
) {
    val valuesMap: ConfigItemMap by lazy { values.associate { it.name to it.value } }
}

enum class PermissionType(val value: String) {
    VIEW("view"),
    CREATE("create"),
    EDIT("edit"),
    SIGNOFF("signoff"),
    ARCHIVE("archive"),
    ASSIGN("assign_task"), // there are other permission type, but only add when supports it
    ;

    companion object {
        fun fromJson(json: JsonElement): List<PermissionType> {
            return when {
                json.isJsonArray -> json.asJsonArray.mapNotNull { value ->
                    values().find { it.value == value.asStringOrNull() }
                }
                json.asStringOrNull() != null -> {
                    if (json.asStringOrNull() == "*") {
                        values().asList()
                    } else {
                        listOf()
                    }
                }
                else -> listOf()
            }
        }
    }
}

data class ConfigCategory(
    val category: String,
    val subcategory: List<String>,
    val options: List<JsonElement>,
)

data class ConfigCreationCategory(
    var title: String,
    var description: String,
    var options: List<Option>,
    var cusvals: List<CustomValue>?,
) {
    data class Option(var opt: String)
}
