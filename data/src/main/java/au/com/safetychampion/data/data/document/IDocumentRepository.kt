package au.com.safetychampion.data.data.document

import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.document.Document
import au.com.safetychampion.data.domain.models.document.DocumentCopySource
import au.com.safetychampion.data.domain.models.document.DocumentSignoff
import au.com.safetychampion.data.domain.models.document.DocumentTask

interface IDocumentRepository {
    suspend fun list(): Result<List<Document>>
    suspend fun fetch(moduleId: String): Result<Document>
    suspend fun copySource(moduleId: String): Result<DocumentCopySource>
    suspend fun combineFetchAndTask(moduleId: String, taskId: String): Result<DocumentSignoff>
    suspend fun signoff(
        moduleId: String,
        taskId: String,
        payload: DocumentTask
    ): Result<DocumentTask>
}
