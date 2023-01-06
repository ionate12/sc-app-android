package au.com.safetychampion.data.domain.models.document

data class DocumentReview(
    var latestCompletedTask: DocumentTask? = null,
    var task: DocumentTask? = null
)
