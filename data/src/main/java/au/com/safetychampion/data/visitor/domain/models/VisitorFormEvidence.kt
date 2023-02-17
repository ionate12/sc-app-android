package au.com.safetychampion.data.visitor.domain.models

import au.com.safetychampion.data.domain.models.TimeField
import au.com.safetychampion.data.domain.models.customvalues.CustomValue

data class VisitorFormEvidence(
    val type: String,
    val _id: String,
    val cusvals: List<CustomValue>,
    val tz: String,
    val date: String,
    val time: TimeField
)
