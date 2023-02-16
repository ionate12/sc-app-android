package au.com.safetychampion.data.domain.models.incidents

import au.com.safetychampion.data.domain.base.BaseModule
import au.com.safetychampion.data.domain.base.BaseTask
import au.com.safetychampion.data.domain.models.ExternalBodyPojo
import au.com.safetychampion.data.domain.models.Tier
import au.com.safetychampion.data.domain.models.UpdateLog
import au.com.safetychampion.data.domain.models.action.ActionLink
import au.com.safetychampion.data.domain.models.action.network.ActionPL
import au.com.safetychampion.data.domain.models.customvalues.CustomValue
import au.com.safetychampion.data.domain.models.login.LoginUser
import au.com.safetychampion.data.domain.uncategory.DocAttachment

data class IncidentTask(
    override val _id: String? = null,
    var attachments: MutableList<DocAttachment> = mutableListOf(),
    override var complete: Boolean? = null,
    val controlLevel: String? = null,
    val controlLevelOther: String? = null,
    val controlReviewed: String? = null,
    val controlReviewedOther: String? = null,
    var cusvals: List<CustomValue> = listOf(),
    val dateCompleted: String? = null,
    override var dateDue: String? = null,
    val dateSignedoff: String? = null,
    override var description: String? = null,
    var editComments: List<UpdateLog> = listOf(),
    var externalBodiesNotified: List<ExternalBodyPojo> = listOf(),
    var externalBodiesNotifiedYesNo: Boolean? = null,
    var links: MutableList<ActionLink> = mutableListOf(),
    val hazardCategory: String? = null,
    val hazardCategoryOther: String? = null,
    var newActions: MutableList<ActionPL> = mutableListOf(),
    val sessionId: String? = null,
    val severity: String? = null,
    val signedoffBy: LoginUser? = null,
    var signatures: List<SignaturePayloadIncident>? = null,
    val tzDateSignedoff: String? = null,
    override var title: String? = null,
    override var _for: BaseModule? = null,
    override var tier: Tier? = null,
    override var type: String? = null,
    override val inExecution: Boolean? = null
) : BaseTask
