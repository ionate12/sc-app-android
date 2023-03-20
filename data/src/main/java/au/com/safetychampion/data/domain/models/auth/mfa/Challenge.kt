package au.com.safetychampion.data.domain.models.auth.mfa

import android.os.Parcelable
import au.com.safetychampion.data.domain.base.BasePL
import kotlinx.parcelize.Parcelize

@Parcelize
data class Challenge(
    val mfaToken: String?,
    val destination: String?,
    val secret: String?,
    val barcodeUri: String?,
    val recoveryCodes: List<String>?,
    val firstTimeEnroll: Boolean?,
    val oob: String?
) : Parcelable

data class ChallengePL(
    val method: String
) : BasePL()
