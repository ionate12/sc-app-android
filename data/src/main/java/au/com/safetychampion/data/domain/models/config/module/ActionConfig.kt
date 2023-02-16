package au.com.safetychampion.data.domain.models.config.module

import au.com.safetychampion.data.domain.core.ModuleType
import au.com.safetychampion.data.domain.models.config.ConfigCreationCategory
import au.com.safetychampion.data.domain.models.config.Configuration
import au.com.safetychampion.data.domain.models.customvalues.CustomValue
import au.com.safetychampion.data.util.extension.asStringOrNull
import au.com.safetychampion.data.util.extension.parseObject

class ActionConfig(config: Configuration) : BaseConfig(config.valuesMap) {
    val titleExtra: ConfigTitleExtra?
    val referencePrefix: String
    val createActionCategoryList: List<ConfigCreationCategory>
    val createActionCusvals: List<CustomValue>
    val taskSignoffActionCusvals: List<CustomValue>
    val taskSignoffHazardCategoryList: List<String>
    val taskSignoffSeverityList: List<String>
    val taskSignoffControlLevelList: List<String>
    val riskMatrixUrl: String?

    init {
        if (config.type != ModuleType.ACTION) {
            throw IllegalArgumentException("set wrong params, module Type must be Action")
        }
        val configMap = config.valuesMap
        this.titleExtra = configMap["TITLE_EXTRA"]?.parseObject()
        this.referencePrefix = configMap["REFERENCE_PREFIX"]?.asStringOrNull() ?: "ACT"
        this.createActionCategoryList = configMap["CREATE_ACTION_CATEGORY_LIST"]?.parseObject() ?: listOf()
        this.createActionCusvals = configMap["CREATE_ACTION_CUSVALS"]?.parseObject() ?: listOf()
        this.taskSignoffActionCusvals = configMap["TASK_SIGNOFF_ACTION_CUSVALS"]?.parseObject() ?: listOf()
        this.taskSignoffHazardCategoryList = configMap["TASK_SIGNOFF_HAZARD_CATEGORY_LIST"]?.parseObject() ?: listOf()
        this.taskSignoffSeverityList = configMap["TASK_SIGNOFF_SEVERTIY_LIST"]?.parseObject() ?: listOf() // notes: "SEVERTIY" is a typo from BE
        this.taskSignoffControlLevelList = configMap["TASK_SIGNOFF_CONTROL_LEVEL_LIST"]?.parseObject() ?: listOf()
        this.riskMatrixUrl = configMap["RISK_MATRIX_IMAGE_LINK"]?.asStringOrNull()
    }
    data class ConfigTitleExtra(
        val labels: Extra
    ) {
        data class Extra(val workerGroupDashboard: String)
        fun getTitleExtra(): String = labels.workerGroupDashboard
    }
}
