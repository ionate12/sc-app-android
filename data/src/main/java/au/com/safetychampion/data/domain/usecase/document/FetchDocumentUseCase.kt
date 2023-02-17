package au.com.safetychampion.data.domain.usecase.document

import au.com.safetychampion.data.data.document.IDocumentRepository
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.document.Document

class FetchDocumentUseCase(
    private val repository: IDocumentRepository
) {
    suspend operator fun invoke(
        moduleId: String
    ): Result<Document> {
        return repository.fetch(moduleId)
    }
}
