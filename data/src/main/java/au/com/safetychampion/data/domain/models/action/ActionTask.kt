package au.com.safetychampion.data.domain.models.action

import au.com.safetychampion.data.domain.models.Tier
import au.com.safetychampion.data.domain.models.UpdateLog
import au.com.safetychampion.data.domain.models.action.payload.ActionPL
import au.com.safetychampion.data.domain.models.customvalues.CustomValue
import au.com.safetychampion.data.domain.models.login.LoginUser
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class ActionTask(
    var complete: Boolean = false,
    var dateCompleted: String? = null,
    var completionNotes: String? = null,
    var controlLevel: String? = null,
    var controlLevelOther: String? = null,
    var dateDue: String? = null,
    var description: String? = null,
    @SerializedName("for")
    var _for: JsonObject? = null,
    var hazardCategory: String? = null,
    var hazardCategoryOther: String? = null,
    var severity: String? = null,
    var tier: Tier? = null,
    var title: String? = null,
    var type: String? = null,
    var _id: String? = null,
    var tzDateSignedoff: String? = null,
    var dateSignedoff: String? = null,
    var signedoffBy: LoginUser? = null,
    var attachments: JsonArray? = null,
    var editComments: List<UpdateLog> = emptyList(),
    var cusvals: List<CustomValue> = emptyList(),
    var links: List<ActionLink> = emptyList(),
    var newActions: List<ActionPL> = emptyList(),
    var changedImplemented: Boolean? = null
)
