package au.com.safetychampion.data.domain.models.reviewplan

import au.com.safetychampion.data.domain.models.Tier
import au.com.safetychampion.data.domain.models.login.LoginUser
import com.google.gson.annotations.SerializedName

data class ReviewPlanEvidenceTask(
    val type: String? = null,
    val _id: String? = null,
    val tier: Tier? = null,
    @SerializedName("for")
    var _for: Tier? = null,
    val complete: Boolean? = null,
    val title: String? = null,
    val description: String? = null,
    val reviewNotes: String? = null,
    val signedoffBy: LoginUser? = null,
    val tzDateSignedoff: String? = null,
    val dateDue: String? = null,
    val dateCompleted: String? = null,
    val dateSignedoff: String? = null
)
