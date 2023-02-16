package au.com.safetychampion.data.domain.models.config.module

import au.com.safetychampion.data.domain.core.ModuleType
import au.com.safetychampion.data.domain.models.config.Configuration

class InspectionConfig(config: Configuration) : BaseConfig(config.valuesMap) {
    init {
        if (config.type != ModuleType.INSPECTION) {
            throw IllegalArgumentException("set wrong params, module Type must be INSPECTION")
        }
    }
}
