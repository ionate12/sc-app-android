package au.com.safetychampion.data.domain.models.incidents

data class InjuredBodyPart(
    var _id: String,
    var bodyPart: String,
    var bodyPartOther: String,
    var injury: String,
    var injuryOther: String,
    var comment: String
)
