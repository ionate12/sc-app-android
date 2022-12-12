package au.com.safetychampion.data.domain.models.visitor

import com.google.gson.JsonObject

data class SCQuillDeltaMessage(
    override val type: String,
    override val value: JsonObject
) : SCBaseMessage
