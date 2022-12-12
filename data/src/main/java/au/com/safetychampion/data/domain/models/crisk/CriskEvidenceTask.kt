package au.com.safetychampion.data.domain.models.crisk

import au.com.safetychampion.data.domain.InspectionFormPayload
import au.com.safetychampion.data.domain.models.Tier
import au.com.safetychampion.data.domain.models.login.LoginUser
import au.com.safetychampion.data.domain.uncategory.DocAttachment
import com.google.gson.annotations.SerializedName

data class CriskEvidenceTask(
    val type: String,
    val _id: String,
    val tier: Tier,
    @SerializedName("for")
    val _for: Crisk,
    val complete: Boolean,
    val confidential: Boolean = false,
    val title: String,
    val description: String,
    val reviewNotes: String,
    val signedoffBy: LoginUser?,
    val tzDateSignedoff: String,
    val signatures: List<InspectionFormPayload.SignaturePayload>,
    val attachments: List<DocAttachment>,
    val dateDue: String,
    val dateCompleted: String,
    val dateSignedoff: String
)
