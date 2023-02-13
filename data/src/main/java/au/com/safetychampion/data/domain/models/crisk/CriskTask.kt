package au.com.safetychampion.data.domain.models.crisk

import au.com.safetychampion.data.domain.Attachment
import au.com.safetychampion.data.domain.base.BaseTask
import au.com.safetychampion.data.domain.core.Signature
import au.com.safetychampion.data.domain.models.IAttachment
import au.com.safetychampion.data.domain.models.ICusval
import au.com.safetychampion.data.domain.models.IPendingAction
import au.com.safetychampion.data.domain.models.ISignature
import au.com.safetychampion.data.domain.models.ISubcategoryCusval
import au.com.safetychampion.data.domain.models.SCHolderLink
import au.com.safetychampion.data.domain.models.Tier
import au.com.safetychampion.data.domain.models.action.ActionLink
import au.com.safetychampion.data.domain.models.action.network.ActionPL
import au.com.safetychampion.data.domain.models.action.network.PendingActionPL
import au.com.safetychampion.data.domain.models.customvalues.CustomValue

interface ICriskTaskComponents :
    ICusval, ISubcategoryCusval, IAttachment, ISignature, IPendingAction

// TODO: redo CriskTask, needs add more properties.
data class CriskTask(
    val actionLinks: MutableList<ActionLink>,
    val currentMitigation: String,
    val futureControl: Boolean,
    val newAction: MutableList<ActionPL>,
    val recurrent: Boolean,
    val residualRisk: String,
    val riskOwnerLinks: MutableList<SCHolderLink>,
    val save: Boolean,
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
    override var subcategoryCusvals: MutableList<CustomValue>,
    override var signatures: MutableList<Signature>,
    override var attachments: MutableList<Attachment>,
    override var pendingActions: MutableList<PendingActionPL>
) : BaseTask, ICusval, ICriskTaskComponents
