package au.com.safetychampion.data.domain.models.config.module

import au.com.safetychampion.data.domain.core.ModuleType
import au.com.safetychampion.data.domain.models.config.Configuration
import au.com.safetychampion.data.util.extension.asBooleanOrDefault
import au.com.safetychampion.data.util.extension.asStringOrNull
import au.com.safetychampion.data.util.extension.parseObject

class CriskConfig(config: Configuration) : BaseConfig(config.valueMaps()) {

    val criskOwnerList: List<String>
    val criskOwnerLookup: List<ConfigHolderLookUp>
    val signatureEnabled: Boolean
    val inherentRiskEnabled: Boolean
    val futureRiskEnabled: Boolean
    val riskMatrixLink: String?
    val classificationList: List<ConfigReviewPlanClassification>
    init {
        if (config.type != ModuleType.CRISK) {
            throw IllegalArgumentException("set wrong params, module Type must be CRISK")
        }
        criskOwnerList = configMap["RISK_OWNER_LIST"]?.parseObject() ?: listOf()
        criskOwnerLookup = configMap["RISK_OWNER_LOOKUP"]?.parseObject() ?: listOf()
        signatureEnabled = configMap["SIGNOFF_HAND_SIGNATURES"].asBooleanOrDefault()
        inherentRiskEnabled = configMap["ENABLE_RISK_RATING_INHERENT"].asBooleanOrDefault()
        futureRiskEnabled = configMap["ENABLE_RISK_RATING_FUTURE"].asBooleanOrDefault()
        riskMatrixLink = configMap["RISK_MATRIX_IMAGE_LINK"]?.asStringOrNull()
        classificationList = configMap["CLASSIFICATION_LIST"]?.parseObject() ?: listOf()
    }
}
