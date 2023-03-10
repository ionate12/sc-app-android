package au.com.safetychampion.data.domain.models.config.module

import au.com.safetychampion.data.domain.core.ModuleType
import au.com.safetychampion.data.domain.models.config.ConfigCategory
import au.com.safetychampion.data.domain.models.config.ConfigCreationCategory
import au.com.safetychampion.data.domain.models.config.Configuration
import au.com.safetychampion.data.domain.models.config.OptionType
import au.com.safetychampion.data.domain.models.customvalues.CustomValue
import au.com.safetychampion.data.util.extension.asIntOrNull
import au.com.safetychampion.data.util.extension.asStringOrNull
import au.com.safetychampion.data.util.extension.parseObject

class IncidentConfig(config: Configuration) : BaseConfig(config.valueMaps()) {
    val referencePrefix: String
    val createIncidentCategoryList: List<ConfigCreationCategory>
    val createIncidentCusvals: List<CustomValue>
    val createInjuredPersonRoles: List<String>
    val createPersonLookup: HrLookup?
    val createBodyPartList: List<ConfigCategory>
    val createLocationList: List<String>
    val createEnvCusvals: List<CustomValue>

    val signoffIncidentCusvals: List<CustomValue>
    val signoffHazardCategoryList: List<String>
    val signoffSeverityList: List<String>
    val signoffControlReviewList: List<String>
    val signoffControlLevelList: List<String>
    val signoffExternalBodyList: List<String>
    val signoffLostTimeInjuryList: List<String>

    val createSignature: OptionType
    val createSignatureRoleList: List<SignatureRole>
    val signoffSignature: OptionType
    val signoffSignatureRoleList: List<SignatureRole>

    val riskMatrixUrl: String?
    val bodyPartImgLink: String?

    init {
        if (config.type != ModuleType.INCIDENT) {
            throw IllegalArgumentException("set wrong params, module Type must be Action")
        }
        this.referencePrefix = configMap["REFERENCE_PREFIX"]?.asStringOrNull() ?: "INC"
        // creation
        this.createIncidentCategoryList = configMap["CREATE_INCIDENT_CATEGORY_LIST"]?.parseObject() ?: listOf()
        this.createIncidentCusvals = configMap["CREATE_INCIDENT_CUSVALS"]?.parseObject() ?: listOf()
        this.createInjuredPersonRoles = configMap["CREATE_INCIDENT_INJURED_PERSON_ROLE_LIST"]?.parseObject() ?: listOf()
        this.createPersonLookup = configMap["CREATE_INCIDENT_INJURED_PERSON_LOOKUP"]?.parseObject()
        this.createBodyPartList = configMap["CREATE_INCIDENT_BODY_PART_INJURY_LIST"]?.parseObject() ?: listOf()
        this.createLocationList = configMap["CREATE_INCIDENT_LOCATION_LIST"]?.parseObject() ?: listOf()
        this.createEnvCusvals = configMap["CREATE_INCIDENT_PROP_ENV_DAMAGE_CUSVALS"]?.parseObject() ?: listOf()
        // signoff
        this.signoffHazardCategoryList = configMap["TASK_SIGNOFF_HAZARD_CATEGORY_LIST"]?.parseObject() ?: listOf()
        this.signoffIncidentCusvals = configMap["TASK_SIGNOFF_INCIDENT_CUSVALS"]?.parseObject() ?: listOf()
        this.signoffSeverityList = configMap["TASK_SIGNOFF_SEVERTIY_LIST"]?.parseObject() ?: listOf() // notes: "SEVERTIY" is a typo from BE
        this.signoffControlReviewList = configMap["TASK_SIGNOFF_CONTROL_REVIEW_LIST"]?.parseObject() ?: listOf()
        this.signoffControlLevelList = configMap["TASK_SIGNOFF_CONTROL_LEVEL_LIST"]?.parseObject() ?: listOf()
        this.signoffExternalBodyList = configMap["TASK_SIGNOFF_EXTERNAL_BODY_LIST"]?.parseObject() ?: listOf()
        this.signoffLostTimeInjuryList = configMap["TASK_SIGNOFF_LOST_TIME_INJURY_LIST"]?.parseObject() ?: listOf()
        // signature
        this.createSignature = (configMap["CREATE_INCIDENT_HAND_SIGNATURES"]?.asIntOrNull() ?: 0)
            .let { OptionType.fromInt(it) }
        this.createSignatureRoleList = configMap["CREATE_INCIDENT_HAND_SIGNATURES_ROLE_LIST"]?.parseObject() ?: listOf()
        this.signoffSignature = (configMap["TASK_SIGNOFF_HAND_SIGNATURES"]?.asIntOrNull() ?: 0)
            .let { OptionType.fromInt(it) }
        this.signoffSignatureRoleList = configMap["TASK_SIGNOFF_HAND_SIGNATURES_ROLE_LIST"]?.parseObject() ?: listOf()

        this.riskMatrixUrl = configMap["RISK_MATRIX_IMAGE_LINK"]?.asStringOrNull()
        this.bodyPartImgLink = configMap["BODY_PARTS_IMAGE_LINK"]?.asStringOrNull()
    }

    data class HrLookup(
        val scope: String
    )
    data class SignatureRole(
        val title: String,
        val options: List<Any>? // Not sure what type it is
    )
}
