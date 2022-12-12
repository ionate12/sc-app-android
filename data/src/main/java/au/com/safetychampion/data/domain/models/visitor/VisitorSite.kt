package au.com.safetychampion.data.domain.models.visitor
import au.com.safetychampion.data.domain.models.CreatedBy
import au.com.safetychampion.data.domain.models.GeoLatLng
import au.com.safetychampion.data.domain.models.Tier
import au.com.safetychampion.data.domain.models.customvalues.CustomValue
import com.google.gson.annotations.SerializedName

/**
 * Created by @Minh_Khoi_MAI on 16/11/20
 */
data class VisitorSite(
    val _id: String,
    val type: String,
    val tier: TierInfo,
    val forms: List<VisitorFormTemplate>,
    val title: String,
    val description: String,
    val category: String,
    val categoryOther: String? = null,
    val subcategory: String,
    val subcategoryOther: String? = null,
    val createdBy: CreatedBy,
    val tzDateCreated: String,
    val dateCreated: String,
    private val geoData: GeoLatLng? = null,
    private val geofenceRadius: Long? = null,
    private val control: Control? = null
)

data class TierInfo(
    val _id: String,
    val type: String,
    val name: String,
    val VISIT_TERMS: VisitTerm,
    val workplace: TierInfo?
)

data class Control(val pin: Boolean, val geoLeave: Boolean, val geoArrive: Boolean)

data class VisitorRole(
    val type: String,
    val title: String,
    val _id: String
)

data class VisitorFormTemplate(
    val role: VisitorRole,
    val arrive: VisitorParentForm?,
    val leave: VisitorParentForm?
)

data class VisitorParentForm(
    var form: VisitorForm,
    val messages: VisitorMessages
)

data class VisitorMessages(
    var pre: SCBaseMessage? = null,
    @SerializedName("in")
    var _in: SCBaseMessage? = null,
    var post: SCBaseMessage? = null
)

class SCTextMessage(override val type: String, override val value: String) : SCBaseMessage

data class VisitorForm(
    val _id: String,
    val type: String,
    val tier: Tier,
    val title: String,
    val description: String,
    var messages: VisitorMessages,
    val createdBy: CreatedBy,
    val tzDateCreated: String,
    val dateCreated: String,
    var cusvals: List<CustomValue>,
    val archived: Boolean?,
    var selectedRole: VisitorRole? = null
)

data class VisitTerm(val arrive: String, val leave: String)

data class VisitorToken(val pinRequired: Boolean?, val token: String?)
