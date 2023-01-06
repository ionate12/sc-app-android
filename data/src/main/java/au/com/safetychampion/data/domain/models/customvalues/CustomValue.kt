package au.com.safetychampion.data.domain.models.customvalues

import com.google.gson.JsonElement

data class CustomValue(
    var _id: String,
    val type: String,
    val title: String,
    val value: JsonElement?
)
