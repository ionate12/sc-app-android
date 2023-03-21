package au.com.safetychampion.data.domain.usecase

import au.com.safetychampion.data.data.local.SyncableRepository
import au.com.safetychampion.data.domain.base.BaseSignOff
import au.com.safetychampion.data.domain.base.BaseTask
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.core.SCError
import au.com.safetychampion.data.domain.core.flatMapError
import au.com.safetychampion.data.util.extension.koinInject

abstract class BaseSignoffUseCase<R : BaseTask, T : BaseSignOff<R>> : BaseUseCase() {
    private val syncableRepo: SyncableRepository by koinInject()

    private suspend fun storeOrReplace(payload: T): Result<R> {
        syncableRepo.insert(payload.syncableKey(), payload)
        return Result.Success(payload.task)
    }

    protected abstract suspend fun signoffOnline(payload: T): Result<R>

    suspend operator fun invoke(payload: T): Result<R> {
        return if (syncableRepo.hasSyncable(payload.syncableKey())) {
            storeOrReplace(payload)
        } else {
            signoffOnline(payload).flatMapError {
                when (it) {
                    is SCError.NoNetwork -> { storeOrReplace(payload) }
                    else -> Result.Error(it)
                }
            }
        }
    }
}
