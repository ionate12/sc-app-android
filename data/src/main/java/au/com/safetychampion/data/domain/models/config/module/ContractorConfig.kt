package au.com.safetychampion.data.domain.models.config.module

import au.com.safetychampion.data.domain.core.ModuleType
import au.com.safetychampion.data.domain.models.config.Configuration

class ContractorConfig(config: Configuration) : BaseConfig(config.valueMaps()) {
    init {
        if (config.type != ModuleType.CONTRACTOR) {
            throw IllegalArgumentException("set wrong params, module Type must be CONTRACTOR")
        }
    }
}
