package au.com.safetychampion.data.domain.models.incidents

import au.com.safetychampion.data.domain.models.TimeField

data class SignaturePayloadIncident(
    val _id: String,
    val name: String,
    val role: String,
    val roleOther: String,
    val date: String,
    val time: TimeField,
    val tz: String
)
