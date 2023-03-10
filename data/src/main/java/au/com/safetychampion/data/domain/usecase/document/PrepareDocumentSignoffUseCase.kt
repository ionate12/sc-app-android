package au.com.safetychampion.data.domain.usecase.document

import au.com.safetychampion.data.data.document.IDocumentRepository
import au.com.safetychampion.data.domain.base.BaseSignOff
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.document.DocumentSignoff
import au.com.safetychampion.data.domain.models.document.DocumentTask
import au.com.safetychampion.data.domain.usecase.BasePrepareSignoffUseCase
import au.com.safetychampion.data.util.extension.koinInject

class PrepareDocumentSignoffUseCase : BasePrepareSignoffUseCase<DocumentTask, DocumentSignoff>() {
    private val repository: IDocumentRepository by koinInject()

    override suspend fun fetchData(moduleId: String, taskId: String?): Result<DocumentSignoff> {
        requireNotNull(taskId)
        return repository.combineFetchAndTask(moduleId, taskId)
    }

    override fun getSyncableKey(moduleId: String, taskId: String?): String {
        requireNotNull(taskId)
        return BaseSignOff.documentSignoffSyncableKey(moduleId, taskId)
    }
}
