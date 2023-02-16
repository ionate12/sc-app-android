package au.com.safetychampion.data.domain.models.config.module

import au.com.safetychampion.data.domain.core.ModuleType
import au.com.safetychampion.data.domain.models.config.Configuration
import au.com.safetychampion.data.domain.models.customvalues.CustomValue
import au.com.safetychampion.data.util.extension.parseObject

class ReviewPlanConfig(config: Configuration) : BaseConfig(config.valuesMap) {

    val classificationList: List<ConfigReviewPlanClassification>
    val holderList: List<String>
    val holderLookUp: List<ConfigHolderLookUp>
    init {
        if (config.type != ModuleType.REVIEW_PLAN) {
            throw IllegalArgumentException("set wrong params, module Type must be REVIEW PLAN")
        }
        val configMap = config.valuesMap
        holderList = configMap["HOLDER_LIST"]?.parseObject() ?: listOf()
        holderLookUp = configMap["HOLDER_LOOKUP"]?.parseObject() ?: listOf()
        classificationList = configMap["CLASSIFICATION_LIST"]?.parseObject() ?: listOf()
    }
}

data class ConfigHolderLookUp(
    val type: String,
    val scope: String
)

data class ConfigReviewPlanClassification(
    val category: String,
    val subcategory: List<Subcategory>,
    val options: List<Option>,
    val cusvals: List<CustomValue>
) {

    data class Option(val opt: String, val value: Boolean)
    data class Subcategory(val title: String, val options: List<Option>, val cusvals: List<CustomValue>)
}
