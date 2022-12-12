package au.com.safetychampion.data.domain

import au.com.safetychampion.data.domain.models.login.LoginUser
import au.com.safetychampion.data.domain.models.login.MultiLogin

data class LoginEnv(
    var loginCredential: String? = null,
    @JvmField
    var user: LoginUser? = null,
    @JvmField
    var token: String? = null
)

data class MultiLoginEnv(
    val item: MultiLogin,
    val token: String,
    var loginCredential: String? = null
)
