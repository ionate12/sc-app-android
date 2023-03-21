package au.com.safetychampion.data.domain.models.auth

import au.com.safetychampion.data.domain.models.workplace.CreatedBy

data class MultiLogin(
    val name: String,
    val email: String,
    val phone: String? = null,
    val phoneCountryCode: String? = null,
    val multiuser: Boolean,
    val options: List<Option>
) {
    data class Option(
        val workerGroupTier: CreatedBy? = null,
        val workplaceTier: CreatedBy? = null,
        val orgTier: CreatedBy? = null,
        val orgAdminTier: CreatedBy? = null,
        val user: User
    )

    data class User(
        val type: String,
        val _id: String,
        val tier: CreatedBy,
        val profile: Profile
    )

    data class Profile(
        val type: String,
        val _id: String,
        val title: String
    )
}
