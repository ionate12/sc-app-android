package au.com.safetychampion.data.domain.models.auth

import au.com.safetychampion.data.domain.base.BasePL

data class LoginPL(
    var email: String,
    var password: String
) : BasePL()
