package au.com.safetychampion.scmobile.visitorModule.models

import au.com.safetychampion.scmobile.database.tier.Tier
import au.com.safetychampion.scmobile.database.visitor.VisitorSiteDB
import au.com.safetychampion.scmobile.modules.common.CreatedBy
import au.com.safetychampion.scmobile.modules.common.SCBaseMessage
import au.com.safetychampion.scmobile.modules.incident.model.CustomValue
import au.com.safetychampion.scmobile.utils.GsonHelper
import au.com.safetychampion.scmobile.utils.getGson
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
) {

    fun getRoles(): List<VisitorRole> = forms.map { it.role }

    fun hasArriveForm(roleId: String): Boolean = forms.find { it.role._id == roleId }?.arrive != null

    fun hasLeaveForm(roleId: String): Boolean = forms.find { it.role._id == roleId }?.leave != null

    fun getArriveFormId(roleId: String) = forms.find { it.role._id == roleId }?.arrive?.form?._id

    fun getLeaveFormId(roleId: String) = forms.find { it.role._id == roleId }?.leave?.form?._id

    fun getFormTemplate(roleId: String) = forms.first { it.role._id == roleId }

    fun toVisitorSiteDB(profileId: String): VisitorSiteDB {
        val data = GsonHelper.getGson().toJsonTree(this).asJsonObject
        return VisitorSiteDB(_id, profileId, type, title, description, category, categoryOther, subcategory, subcategoryOther, data)
    }

    fun getGeofenceData(): GeofenceData? = geoData?.let {
        GeofenceData(
            it.toLatLng(),
            geofenceRadius!!,
            control?.geoLeave ?: false,
            control?.geoArrive ?: false
        )
    }

    data class TierInfo(
        val _id: String,
        val type: String,
        val name: String,
        val VISIT_TERMS: VisitTerm,
        val workplace: TierInfo?
    )

    data class Control(val pin: Boolean, val geoLeave: Boolean, val geoArrive: Boolean)
}

data class VisitorRole(
    val type: String,
    val title: String,
    val _id: String
)

data class VisitorFormTemplate(
    val role: VisitorRole,
    val arrive: VisitorParentForm?,
    val leave: VisitorParentForm?
) {
    fun canDisplayArrive() = arrive != null && (arrive.getUsableMessages()._in != null || arrive.form.cusvals.isNotEmpty())

    fun canDisplayLeave() = leave != null && (leave.getUsableMessages()._in != null || leave.form.cusvals.isNotEmpty())
}

data class VisitorParentForm(
    var form: VisitorForm,
    val messages: VisitorMessages
) {
    /**
     * Override form messages if site messages is not null
     */
    fun getUsableMessages(): VisitorMessages {
        try {
            return VisitorMessages(
                pre = messages.pre ?: form.messages.pre,
                _in = messages._in ?: form.messages._in,
                post = messages.post ?: form.messages.post
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return VisitorMessages()
        }
    }
}

data class VisitorMessages(
    var pre: SCBaseMessage? = null,
    @SerializedName("in")
    var _in: SCBaseMessage? = null,
    var post: SCBaseMessage? = null
) {
    fun clone(): VisitorMessages {
        return getGson().fromJson(
            getGson().toJson(this),
            this::class.java
        )
    }
}

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
    val archived: Boolean?
) {
    var selectedRole: VisitorRole? = null
    fun toPayload() = VisitorPayload.Form(
        _id,
        type,
        cusvals.filter { it.hasValue() }.map { it.toJsonSubmission() }
    )
}

data class VisitTerm(val arrive: String, val leave: String)

data class VisitorToken(val pinRequired: Boolean?, val token: String?)
