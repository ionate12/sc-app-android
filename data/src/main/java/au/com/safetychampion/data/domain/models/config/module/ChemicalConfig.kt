package au.com.safetychampion.data.domain.models.config.module

import au.com.safetychampion.data.domain.core.ModuleType
import au.com.safetychampion.data.domain.models.config.ConfigCategory
import au.com.safetychampion.data.domain.models.config.Configuration
import au.com.safetychampion.data.util.extension.parseObject

class ChemicalConfig(config: Configuration) : BaseConfig(config.valuesMap) {
    val locationList: List<ConfigCategory>
    val customInfo: CustomInformation?
    init {
        if (config.type != ModuleType.CHEMICAL) {
            throw IllegalArgumentException("set wrong params, module Type must be CHEMICAL")
        }
        val configMap = config.valuesMap
        locationList = configMap["LOCATION_LIST"]?.parseObject() ?: listOf()
        customInfo = configMap["CUSTOM_INFORMATION"]?.parseObject()
    }

    data class CustomInformation(
        val title: String,
        val text: String?,
        val link: String?
    )
}
