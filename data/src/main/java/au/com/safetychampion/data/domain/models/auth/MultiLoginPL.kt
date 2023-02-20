package au.com.safetychampion.data.domain.models.auth

import au.com.safetychampion.data.domain.base.BasePL

data class MultiLoginPL(
    val user: User
) : BasePL() {
    data class User(val _id: String)
}
