package au.com.safetychampion.data.domain.models.document

data class DocumentSignoff(
    val body: Document,
    val task: DocumentSignoffTask,
    val taskId: String,
    val moduleId: String
)
