package au.com.safetychampion.data.domain.models.config.module

import au.com.safetychampion.data.domain.core.ModuleType
import au.com.safetychampion.data.domain.models.config.Configuration

class SafetyPlanConfig(config: Configuration) : BaseConfig(config.valuesMap) {
    init {
        if (config.type != ModuleType.SAFETY_PLAN) {
            throw IllegalArgumentException("set wrong params, module Type must be SAFETY_PLAN")
        }
    }
}
