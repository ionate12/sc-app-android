package au.com.safetychampion.data.domain.models.auth

import androidx.room.* // ktlint-disable no-wildcard-imports
import au.com.safetychampion.data.domain.core.ModuleType
import au.com.safetychampion.data.domain.models.Tier
import java.io.Serializable

data class LoginUser(
    val type: ModuleType = ModuleType.USER,
    val _id: String,
    val name: String,
    val email: String,
    val phone: String? = null,
    val phoneCountryCode: String? = null,
    val tier: Tier,
    val configuration: List<Configuration>? = null
) : Serializable {
    override fun toString(): String {
        return "LoginUser{" +
            "name='" + name + '\'' +
            ", email='" + email + '\'' +
            '}'
    }
}
