package au.com.safetychampion.data.domain.models.incidents

import au.com.safetychampion.data.domain.models.ExternalBodyPojo
import au.com.safetychampion.data.domain.models.Tier
import au.com.safetychampion.data.domain.models.UpdateLog
import au.com.safetychampion.data.domain.models.action.ActionBody
import au.com.safetychampion.data.domain.models.action.ActionLink
import au.com.safetychampion.data.domain.models.customvalues.CusvalPojo
import au.com.safetychampion.data.domain.models.login.LoginUser
import au.com.safetychampion.data.domain.uncategory.DocAttachment
import com.google.gson.annotations.SerializedName

data class IncidentSignOffTaskPojo(
    val sessionId: String? = null,
    val severity: String? = null,
    val changeNotes: String? = null,
    var attachments: MutableList<DocAttachment> = mutableListOf(),
    val changeImplemented: Boolean? = null,
    val controlReviewedOther: String? = null,
    var cusvals: List<CusvalPojo> = listOf(),
    val tzDateSignedoff: String? = null,
    val dateSignedoff: String? = null,
    val lostTimeInjury: String? = null,
    val hazardCategory: String? = null,
    val hazardCategoryOther: String? = null,
    var externalBodiesNotified: List<ExternalBodyPojo> = listOf(),
    var externalBodiesNotifiedYesNo: Boolean? = null,
    val controlReviewed: String? = null,
    val controlLevelOther: String? = null,
    val dateCompleted: String? = null,
    val controlLevel: String? = null,
    var links: MutableList<ActionLink> = mutableListOf(),
    var complete: Boolean? = null,
    var editComments: List<UpdateLog> = listOf(),
    var newActions: MutableList<ActionBody> = mutableListOf(),
    // Task input one.
    var dateDue: String? = null,
    var description: String? = null,
    var title: String? = null,
    @SerializedName("for")
    var _for: Tier? = null,
    var tier: Tier? = null,
    var type: String? = null,
    val _id: String? = null,
    val signedoffBy: LoginUser? = null,
    var signatures: List<SignaturePayloadIncident> ? = null
)
