package au.com.safetychampion.data.domain.usecase.document

import au.com.safetychampion.data.data.document.IDocumentRepository
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.document.DocumentSignoff
import au.com.safetychampion.data.domain.models.document.DocumentTask
import au.com.safetychampion.data.domain.usecase.BaseSignoffUseCase
import au.com.safetychampion.data.util.extension.koinInject

class SignoffDocumentUseCase : BaseSignoffUseCase<DocumentTask, DocumentSignoff>() {
    private val repository: IDocumentRepository by koinInject()

    override suspend fun signoffOnline(payload: DocumentSignoff): Result<DocumentTask> {
        payload.task.apply {
            if (dateDueFrom.contains("Date Completed")) {
                dateDueFrom = "DATE_COMPLETED"
            } else if (dateDueFrom.contains("Due Date")) {
                dateDueFrom = "DATE_DUE"
            } else if (hasNewVersion) {
                dateDueFrom = ""
            }

            if (!hasNewVersion) {
                name = null
                description = null
                category = null
                categoryOther = null
                subcategory = null
                subcategoryOther = null
                dateIssued = null
                attachments = null
                upVersionFrom = null
                copy = null
                customVersion = null
            }
        }
        return repository.signoff(
            moduleId = payload.body._id!!,
            taskId = payload.task._id!!,
            payload = payload.task
        )
    }
}
