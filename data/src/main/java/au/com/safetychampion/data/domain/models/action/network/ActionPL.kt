package au.com.safetychampion.data.domain.models.action.network

import au.com.safetychampion.data.domain.Attachment
import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.models.CreatedBy
import au.com.safetychampion.data.domain.models.IAttachment
import au.com.safetychampion.data.domain.models.ICategoryCusval
import au.com.safetychampion.data.domain.models.ICusval
import au.com.safetychampion.data.domain.models.Tier
import au.com.safetychampion.data.domain.models.customvalues.CustomValue

data class ActionPL(
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
    val type: String,

    override var categoryCusvals: MutableList<CustomValue>,
    override var cusvals: MutableList<CustomValue>,
    override var attachments: MutableList<Attachment>?
) : BasePL(), ICusval, ICategoryCusval, IAttachment

data class ActionPL2(
    val date: String,
    val tzDateCreated: String,
    val dateIdentified: String,
    val personReporting: String,
    val overview: String,
    val category: String,
    val categoryOther: String? = null,
    val dateDue: String,
    val personResponsible: String,
    val personResponsibleEmail: String? = null,
    val description: String,
    val tz: String,
    override var cusvals: MutableList<CustomValue>,
    override var categoryCusvals: MutableList<CustomValue>,
    override var attachments: MutableList<Attachment>? = mutableListOf(),
    val comment: String? = null // For Edit only
) : BasePL(), ICusval, ICategoryCusval, IAttachment
