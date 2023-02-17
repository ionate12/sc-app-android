package au.com.safetychampion.data.domain.usecase

import au.com.safetychampion.data.data.local.SyncableRepository
import au.com.safetychampion.data.domain.base.BaseSignOff
import au.com.safetychampion.data.domain.base.BaseTask
import au.com.safetychampion.data.domain.core.* // ktlint-disable no-wildcard-imports
import au.com.safetychampion.data.util.extension.koinInject

abstract class BasePrepareSignoffUseCase<R : BaseTask, T : BaseSignOff<R>> : BaseUseCase() {

    private val syncableRepo: SyncableRepository by koinInject()

    private suspend inline fun <reified T> fromSyncable(moduleId: String, taskId: String?): Result<T> {
        val key = getSyncableKey(moduleId, taskId)
        return syncableRepo.getSyncableData(key)
    }

    abstract suspend fun fetchData(moduleId: String, taskId: String? = null): Result<T>

    abstract fun getSyncableKey(moduleId: String, taskId: String? = null): String

    suspend operator fun invoke(moduleId: String, taskId: String? = null): Result<T> {
        val synableData = fromSyncable<Result<T>>(moduleId, taskId).dataOrNull()
        if (synableData != null) {
            return synableData
        }
        return fetchData(moduleId, taskId)
    }
}
