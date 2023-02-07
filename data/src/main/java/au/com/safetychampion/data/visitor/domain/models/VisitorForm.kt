package au.com.safetychampion.data.visitor.domain.models

import au.com.safetychampion.data.domain.models.CreatedBy
import au.com.safetychampion.data.domain.models.Tier
import au.com.safetychampion.data.domain.models.customvalues.CustomValue

data class VisitorForm(
    val _id: String,
    val type: String,
    val tier: Tier,
    val title: String,
    val description: String,
    var messages: VisitorMessages,
    val createdBy: CreatedBy,
    val tzDateCreated: String,
    val dateCreated: String,
    var cusvals: List<CustomValue>,
    val archived: Boolean?,
    var selectedRole: VisitorRole? = null
)
