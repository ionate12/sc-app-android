package au.com.safetychampion.data.domain.models.action.payload

import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.models.CreatedBy
import au.com.safetychampion.data.domain.models.Tier
import au.com.safetychampion.data.domain.models.customvalues.CustomValue
import au.com.safetychampion.data.domain.models.customvalues.ICategoryCusval
import au.com.safetychampion.data.domain.models.customvalues.ICusval
import au.com.safetychampion.data.domain.uncategory.DocAttachment

data class ActionPL(
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

    override var categoryCusvals: MutableList<CustomValue>,
    override var cusvals: MutableList<CustomValue>
) : BasePL(), ICusval, ICategoryCusval
