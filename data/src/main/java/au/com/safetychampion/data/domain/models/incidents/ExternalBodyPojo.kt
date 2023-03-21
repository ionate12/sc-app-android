package au.com.safetychampion.data.domain.models.incidents

data class ExternalBodyPojo(
    var viewId: Int? = (Math.random() * 10000).toInt(),
    var date: String? = null,
    var name: String? = null,
    var nameOther: String? = null,
    var comment: String? = null
)
