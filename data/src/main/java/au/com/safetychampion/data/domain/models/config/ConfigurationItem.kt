package au.com.safetychampion.data.domain.models.config

import com.google.gson.JsonElement

data class ConfigurationItem(
    val name: String,
    var value: JsonElement // Value can be any.
)
