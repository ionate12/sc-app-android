package au.com.safetychampion.data.domain.usecase.document
import au.com.safetychampion.data.data.document.IDocumentRepository
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.document.DocumentCopySource
import au.com.safetychampion.data.util.extension.koinInject

class FetchCopySourceUseCase {
    private val repository: IDocumentRepository by koinInject()
    suspend operator fun invoke(moduleId: String): Result<DocumentCopySource> {
        return repository.copySource(moduleId)
    }
}
