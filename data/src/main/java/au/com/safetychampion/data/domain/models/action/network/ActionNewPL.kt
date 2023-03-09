package au.com.safetychampion.data.domain.models.action.network

import au.com.safetychampion.data.domain.Attachment
import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.models.* // ktlint-disable no-wildcard-imports
import au.com.safetychampion.data.domain.models.customvalues.CustomValue

/** Payload using for new action + edit; */
// TODO ("review attachment")
data class ActionNewPL(
    val comment: String? = null, // For Edit only
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
    override var attachments: MutableList<Attachment> = mutableListOf()
) : BasePL(), ICusval, ICategoryCusval, IAttachment
