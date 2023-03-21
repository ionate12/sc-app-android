package au.com.safetychampion.data.domain.models.action

import au.com.safetychampion.data.domain.core.ModuleType
import au.com.safetychampion.data.domain.models.ICategoryCusval
import au.com.safetychampion.data.domain.models.ICusval
import au.com.safetychampion.data.domain.models.Tier
import au.com.safetychampion.data.domain.models.customvalues.CustomValue
import au.com.safetychampion.data.domain.models.workplace.CreatedBy
import au.com.safetychampion.data.domain.models.workplace.UpdateLog
import au.com.safetychampion.data.domain.uncategory.DocAttachment

data class Action(
    val _id: String,
    val type: ModuleType = ModuleType.ACTION,
    val tier: Tier,
    val overview: String,
    val description: String,
    val personReporting: String,
    val personResponsible: String,
    val personResponsibleEmail: String? = null,
    val attachments: List<DocAttachment> = listOf(),
    val category: String,
    val categoryOther: String? = null,
    val dateIdentified: String,
    val dateDue: String,
    val createdBy: CreatedBy,
    val tzDateCreated: String,
    val dateCreated: String,
    val reference: String?,
    val editComments: List<UpdateLog>? = null,
    val closed: Boolean = false,
    val archived: Boolean = false,
    val severity: String?,
    override var categoryCusvals: MutableList<CustomValue>,
    override var cusvals: MutableList<CustomValue>
) : ICusval, ICategoryCusval {

    fun toActionLink(): ActionLink = ActionLink(type.value, _id, reference)
}
