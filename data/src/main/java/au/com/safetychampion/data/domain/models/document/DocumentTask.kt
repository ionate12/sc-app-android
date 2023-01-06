package au.com.safetychampion.data.domain.models.document

data class DocumentTask(
    var dateCompleted: String? = null,
    var dateDue: String? = null,
    var dateDueReference: String? = null,
    var dateSignedoff: String? = null,
    var reviewFrequencyKey: String? = null,
    var reviewFrequencyValue: Int? = null,
    var type: String? = null,
    var _id: String? = null
)
