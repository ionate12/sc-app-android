package au.com.safetychampion.data.domain.models.action.payload

import au.com.safetychampion.data.domain.base.CustomValuePL
import au.com.safetychampion.data.domain.models.CreatedBy
import au.com.safetychampion.data.domain.models.Tier
import au.com.safetychampion.data.domain.models.customvalues.CustomValue
import au.com.safetychampion.data.domain.uncategory.DocAttachment

data class Action(
    val attachments: MutableList<DocAttachment>,
    val category: String,
    val date: String,
    val dateDue: String,
    val dateIdentified: String,
    val description: String,
    val overview: String,
    val personReporting: String,
    val otherCategory: List<String>,
    val personResponsible: String,
    val personResponsibleEmail: String,
    val tz: String? = null,
    val tzDateCreated: String,

    val closed: Boolean? = null,
    val createdBy: CreatedBy,
    val comment: String,
    val newComment: String,
    val dateCreated: String,
    val reference: String,
    val severity: String,
    val tier: Tier,
    val _id: String?,
    override val categoryCusvals: MutableList<CustomValue>,
    override val cusvals: MutableList<CustomValue>
) : CustomValuePL()
