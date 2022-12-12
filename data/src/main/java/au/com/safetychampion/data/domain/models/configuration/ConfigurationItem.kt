package au.com.safetychampion.data.domain.models.configuration

import com.google.gson.JsonElement

data class ConfigurationItem(
    @JvmField
    var name: String? = null,
    var value: JsonElement? = null
)
