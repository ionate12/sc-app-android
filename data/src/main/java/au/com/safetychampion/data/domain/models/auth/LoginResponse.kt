package au.com.safetychampion.data.domain.models.auth

sealed class LoginResponse {
    data class Single(val data: LoginEnv) : LoginResponse()
    data class Multi(val data: MultiLoginEnv) : LoginResponse()
    data class MFA(val data: MfaEnv) : LoginResponse()
}
