package au.com.safetychampion.data.domain.models.config.module

import au.com.safetychampion.data.domain.core.ModuleType
import au.com.safetychampion.data.domain.models.config.ConfigItemMap
import au.com.safetychampion.data.domain.models.config.Configuration
import au.com.safetychampion.data.domain.models.config.PermissionType
import au.com.safetychampion.data.util.extension.asStringOrNull

abstract class BaseConfig(protected val configMap: ConfigItemMap) {
    val permissions: List<PermissionType>
    val morphPermissions: List<PermissionType>
    val title: String
    init {
        permissions = configMap["PERMISSION"]?.let { PermissionType.fromJson(it) } ?: listOf()
        morphPermissions = configMap["MORPH_PERMISSION"]?.let { PermissionType.fromJson(it) } ?: listOf()
        title = configMap["TITLE"]?.asStringOrNull() ?: ""
    }

    companion object {
        fun create(configList: List<Configuration>): Map<ModuleType, BaseConfig> = buildMap {
            configList.forEach { config ->
                val data = when (config.type) {
                    ModuleType.NOT_SUPPORTED,
                    ModuleType.USER,
                    -> null
                    ModuleType.ACTION -> ActionConfig(config)
                    ModuleType.CHEMICAL -> ChemicalConfig(config)
                    ModuleType.CRISK -> CriskConfig(config)
                    ModuleType.ADMIN -> AdminConfig(config)
                    ModuleType.CONTRACTOR -> ContractorConfig(config)
                    ModuleType.HR -> HrConfig(config)
                    ModuleType.INCIDENT -> IncidentConfig(config)
                    ModuleType.INSPECTION -> InspectionConfig(config)
                    ModuleType.NOTICEBOARD -> NoticeBoardConfig(config)
                    ModuleType.REVIEW_PLAN -> ReviewPlanConfig(config)
                    ModuleType.SAFETY_PLAN -> SafetyPlanConfig(config)
                    ModuleType.THEME -> ThemeConfig(config)
                    ModuleType.TRAINING -> TrainingConfig(config)
                    ModuleType.DOCUMENT -> DocumentConfig(config)
                }
                if (data != null) {
                    config.type to data
                }
            }
        }
    }
}
