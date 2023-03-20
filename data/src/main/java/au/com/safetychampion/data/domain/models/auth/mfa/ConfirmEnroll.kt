package au.com.safetychampion.data.domain.models.auth.mfa

import au.com.safetychampion.data.domain.base.BasePL

/** Response for ConfirmEnroll, just a empty response*/
class ConfirmEnroll()

data class ConfirmEnrollPL(
    val method: String,
    val mfaToken: String,
    val otp: String,
    val oob: String
) : BasePL()
