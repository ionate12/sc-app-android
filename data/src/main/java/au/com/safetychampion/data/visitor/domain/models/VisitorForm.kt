package au.com.safetychampion.data.visitor.domain.models

import au.com.safetychampion.data.domain.models.ICusval
import au.com.safetychampion.data.domain.models.Tier
import au.com.safetychampion.data.domain.models.customvalues.CustomValue
import au.com.safetychampion.data.domain.models.workplace.CreatedBy

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
    override var cusvals: MutableList<CustomValue>,
    val archived: Boolean?,
    var selectedRole: VisitorRole? = null
) : ICusval {
    fun toPayload(): VisitorPayload.Form {
        return VisitorPayload.Form(
            _id,
            type,
            cusvals
        )
    }
}
