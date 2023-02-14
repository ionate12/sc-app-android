package au.com.safetychampion.data.domain.models.auth

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class LoginEnv(
    val user: LoginUser,
    val token: String
)

data class MultiLoginEnv(
    val item: MultiLogin,
    val token: String
)

@Parcelize
data class MfaEnv(
    val mfa: Data
) : Parcelable {
    @Parcelize
    data class Data(
        val mfaToken: String,
        val oob: String?,
        val destination: String,
        val method: Method
    ) : Parcelable
    enum class Method(val value: String) {
        APP("app"), SMS("sms"), EMAIL("email");
        companion object {
            fun fromString(value: String): Method? = values().find { it.value == value }
        }
    }
}
