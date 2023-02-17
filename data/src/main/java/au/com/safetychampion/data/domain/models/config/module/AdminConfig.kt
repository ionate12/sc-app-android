package au.com.safetychampion.data.domain.models.config.module

import au.com.safetychampion.data.domain.core.ModuleType
import au.com.safetychampion.data.domain.models.Tier
import au.com.safetychampion.data.domain.models.config.Configuration
import au.com.safetychampion.data.domain.models.config.OptionType
import au.com.safetychampion.data.util.extension.asIntOrNull
import au.com.safetychampion.data.util.extension.parseObject

class AdminConfig(config: Configuration) : BaseConfig(config.valueMaps()) {
    val lowerTierAccess: List<Tier>
    val mfa: OptionType
    init {
        if (config.type != ModuleType.ADMIN) {
            throw IllegalArgumentException("set wrong params, module Type must be Action")
        }
        lowerTierAccess = configMap["LOWER_TIER_ACCESS"]?.parseObject() ?: listOf()
        mfa = (configMap["MFA"]?.asIntOrNull() ?: 0).let { OptionType.fromInt(it) }
    }
}
