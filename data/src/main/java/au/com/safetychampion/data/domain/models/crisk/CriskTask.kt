package au.com.safetychampion.data.domain.models.crisk

import androidx.lifecycle.MutableLiveData
import au.com.safetychampion.data.domain.Attachment
import au.com.safetychampion.data.domain.base.BaseTask
import au.com.safetychampion.data.domain.core.Signature
import au.com.safetychampion.data.domain.models.* // ktlint-disable no-wildcard-imports
import au.com.safetychampion.data.domain.models.action.ActionLink
import au.com.safetychampion.data.domain.models.action.network.PendingActionPL
import au.com.safetychampion.data.domain.models.customvalues.CustomValue

interface ICriskTaskComponents :
    ICusval, ISubcategoryCusval, IAttachment, ISignature, IPendingAction

class CriskTask(
    override var _id: String = "",
    override var _for: Crisk? = null,
    var actionLinks: MutableList<ActionLink> = mutableListOf(),
    var archiveNotes: String? = null,
    var autoSuggestShowing: Boolean = false,
    override var attachments: MutableList<Attachment> = mutableListOf(),
    override var complete: Boolean = true,
    var currentMitigation: String? = null,
    override var cusvals: MutableList<CustomValue> = mutableListOf(),
    override var dateDue: String? = null,
    var dateCompleted: String? = null,
    var dateExpiry: String? = null,
    var dateIssued: String? = null,
    override var description: String = "",
    var futureControl: Boolean? = null,
    var futureMitigation: String? = null,
    var futureRiskRating: String? = null,
    var futureRiskRatingOther: String? = null,
    var inherentRiskRating: String? = null,
    var inherentRiskRatingOther: String? = null,
    override val inExecution: Boolean?,
    var newActions: MutableList<PendingActionPL> = mutableListOf(),
    var notes: String? = null,
    override var pendingActions: MutableList<PendingActionPL>,
    var recurrent: Boolean = true,
    var referenceId: String? = null,
    var residualRisk: String? = null,
    var residualRiskOther: String? = null,
    var reviewNotes: String? = null,
    var riskOwner: MutableLiveData<String> = MutableLiveData(),
    var riskOwnerLinks: MutableList<SCHolderLink> = mutableListOf(),
    var riskOwnerOther: String? = null,
    var save: Boolean = false,
    override var signatures: MutableList<Signature> = mutableListOf(),
    override var subcategoryCusvals: MutableList<CustomValue> = mutableListOf(),
    override var title: String? = null,
    override val tier: Tier?,
    override val type: String?,
    var tzDateSignedoff: String? = null
) : BaseTask, ICusval, ICriskTaskComponents
