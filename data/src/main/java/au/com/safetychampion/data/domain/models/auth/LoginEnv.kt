package au.com.safetychampion.data.domain.models.auth

import android.os.Parcelable

data class LoginEnv(
    val user: LoginUser,
    val token: String
)

data class LoginEnvFromMulti(
    val item: LoginUser,
    val token: String
)

data class MultiLoginEnv(
    val item: MultiLogin,
    val token: String
)

@kotlinx.parcelize.Parcelize
data class MfaEnv(
    val mfa: Data
) : Parcelable {
    @kotlinx.parcelize.Parcelize
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
