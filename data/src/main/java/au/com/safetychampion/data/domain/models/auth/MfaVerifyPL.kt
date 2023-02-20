package au.com.safetychampion.data.domain.models.auth

import au.com.safetychampion.data.domain.base.BasePL

data class MfaVerifyPL(
    val otp: String,
    val mfaToken: String,
    val oob: String? = null
) : BasePL()
