package au.com.safetychampion.data.domain.models.login

import androidx.databinding.ObservableField

data class LoginBody(
    var email: String? = null,
    var password: String? = null,
    val emailError: ObservableField<Int> = ObservableField<Int>(),
    val passwordError: ObservableField<Int> = ObservableField<Int>()
)
