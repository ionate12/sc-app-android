package au.com.safetychampion.data.domain.models.crisk

import au.com.safetychampion.data.domain.SignaturePayload
import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.base.BaseTask
import au.com.safetychampion.data.domain.models.SCHolderLink
import au.com.safetychampion.data.domain.models.Tier
import au.com.safetychampion.data.domain.models.action.ActionLink
import au.com.safetychampion.data.domain.models.action.network.ActionPL
import au.com.safetychampion.data.domain.models.customvalues.CustomValue
import au.com.safetychampion.data.domain.models.customvalues.ICategoryCusval
import au.com.safetychampion.data.domain.models.customvalues.ICusval
import au.com.safetychampion.data.domain.uncategory.DocAttachment

data class CriskTask(
    val attachments: MutableList<DocAttachment>,
    val actionLinks: MutableList<ActionLink>,
    val currentMitigation: String,
    val futureControl: Boolean,
    val newAction: MutableList<ActionPL>,
    val recurrent: Boolean,
    val residualRisk: String,
    val riskOwnerLinks: MutableList<SCHolderLink>,
    val save: Boolean,
    val signatures: MutableList<SignaturePayload>,
    val tzDateSignedoff: String,
    override val complete: Boolean?,
    override val dateDue: String?,
    override val description: String?,
    override val _for: Crisk?,
    override val inExecution: Boolean?,
    override val tier: Tier?,
    override val title: String?,
    override val _id: String,
    override val type: String,
    override var cusvals: MutableList<CustomValue>,
    override var categoryCusvals: MutableList<CustomValue>
) : BasePL(), BaseTask, ICusval, ICategoryCusval
