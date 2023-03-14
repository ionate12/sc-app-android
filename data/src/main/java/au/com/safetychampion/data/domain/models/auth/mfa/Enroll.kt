package au.com.safetychampion.data.domain.models.auth.mfa

import android.os.Parcelable
import au.com.safetychampion.data.domain.base.BasePL
import kotlinx.parcelize.Parcelize

@Parcelize
data class Enroll(
    val mfaToken: String,
    val secret: String?,
    val oob: String?,
    val barcodeUri: String?
) : Parcelable

data class EnrollPL(
    val method: String,
    val mfaToken: String,
    val otp: String,
    val oob: String
) : BasePL()
