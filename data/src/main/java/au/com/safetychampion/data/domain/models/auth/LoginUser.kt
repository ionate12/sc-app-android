package au.com.safetychampion.data.domain.models.auth

import au.com.safetychampion.data.domain.core.ModuleType
import au.com.safetychampion.data.domain.models.Tier
import au.com.safetychampion.data.domain.models.config.Configuration
import java.io.Serializable

data class LoginUser(
    val type: ModuleType = ModuleType.USER,
    val _id: String,
    val name: String,
    val email: String,
    val phone: String? = null,
    val phoneCountryCode: String? = null,
    val tier: Tier,
    val configuration: List<Configuration>
) : Serializable {
    override fun toString(): String {
        return "LoginUser{" +
            "name='" + name + '\'' +
            ", email='" + email + '\'' +
            '}'
    }
}
