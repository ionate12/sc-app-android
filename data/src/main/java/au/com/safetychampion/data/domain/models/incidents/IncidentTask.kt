package au.com.safetychampion.data.domain.models.incidents

import au.com.safetychampion.data.domain.base.BaseModule
import au.com.safetychampion.data.domain.base.BaseTask
import au.com.safetychampion.data.domain.core.Signature
import au.com.safetychampion.data.domain.models.*
import au.com.safetychampion.data.domain.models.action.ActionLink
import au.com.safetychampion.data.domain.models.action.network.ActionNewPL
import au.com.safetychampion.data.domain.models.auth.LoginUser
import au.com.safetychampion.data.domain.models.customvalues.CustomValue
import au.com.safetychampion.data.domain.models.workplace.UpdateLog
import au.com.safetychampion.data.domain.uncategory.DocAttachment
import com.google.gson.annotations.SerializedName

data class IncidentTask(
    @SerializedName("taskId")
    override val _id: String? = null,
    var attachments: MutableList<DocAttachment> = mutableListOf(),
    override var complete: Boolean? = null,
    val changeNote: String? = null,
    val changeImplemented: String? = null,
    val controlLevel: String? = null,
    val controlLevelOther: String? = null,
    val controlReviewed: String? = null,
    val controlReviewedOther: String? = null,
    override var cusvals: MutableList<CustomValue> = mutableListOf(),
    val dateCompleted: String? = null,
    override var dateDue: String? = null,
    val dateSignedoff: String? = null,
    override var description: String? = null,
    var editComments: List<UpdateLog> = listOf(),
    var externalBodiesNotified: List<ExternalBodyPojo> = listOf(),
    var links: MutableList<ActionLink> = mutableListOf(),
    val lostTimeInjury: String? = null,
    val hasExternalBody: Boolean? = null,
    val hazardCategory: String? = null,
    val hazardCategoryOther: String? = null,
    var newActions: MutableList<ActionNewPL> = mutableListOf(),
    val sessionId: String? = null,
    val severity: String? = null,
    val signedoffBy: LoginUser? = null,
    override var signatures: MutableList<Signature> = mutableListOf(),
    val tzDateSignedoff: String? = null,
    override var title: String? = null,
    override var _for: BaseModule? = null,
    override var tier: Tier? = null,
    override var type: String? = null,
    override val inExecution: Boolean? = null
) : BaseTask, ISignature, ICusval
