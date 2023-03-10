package au.com.safetychampion.data.domain.models.document

import au.com.safetychampion.data.domain.base.BaseSignOff
import au.com.safetychampion.data.domain.core.ModuleType

data class DocumentSignoff(
    val body: Document,
    override val task: DocumentTask
) : BaseSignOff<DocumentTask> {
    override fun syncableKey() = BaseSignOff.documentSignoffSyncableKey(body._id!!, task._id!!)

    override fun type(): ModuleType = ModuleType.DOCUMENT
}
