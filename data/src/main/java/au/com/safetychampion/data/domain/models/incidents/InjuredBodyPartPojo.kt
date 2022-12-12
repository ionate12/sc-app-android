package au.com.safetychampion.data.domain.models.incidents

data class InjuredBodyPartPojo(
    var _id: String? = null,
    var bodyPart: String? = null,
    var bodyPartOther: String? = null,
    var injury: String? = null,
    var injuryOther: String? = null,
    var comment: String? = null
)
