package au.com.safetychampion.data.data.document

import au.com.safetychampion.data.data.BaseRepository
import au.com.safetychampion.data.data.api.DocumentAPI
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.core.flatMap
import au.com.safetychampion.data.domain.models.document.* // ktlint-disable no-wildcard-imports

class DocumentRepository : BaseRepository(), IDocumentRepository {
    override suspend fun list(): Result<List<Document>> {
        return DocumentAPI.List().call()
    }

    override suspend fun fetch(moduleId: String): Result<Document> {
        return DocumentAPI.Fetch(moduleId).call()
    }

    override suspend fun copySource(moduleId: String): Result<DocumentCopySource> {
        return DocumentAPI.CopySource(moduleId).call()
    }

    override suspend fun combineFetchAndTask(moduleId: String, taskId: String): Result<DocumentSignoff> {
        return fetch(moduleId).flatMap {
            Result.Success(
                DocumentSignoff(
                    body = it,
                    task = DocumentTask(
                        _id = taskId
                    )
                )
            )
        }
    }

    override suspend fun signoff(
        moduleId: String,
        taskId: String,
        payload: DocumentTask
    ): Result<DocumentTask> {
        return DocumentAPI.Signoff(
            moduleId = moduleId,
            taskId = taskId,
            body = payload
        ).call()
    }
}
