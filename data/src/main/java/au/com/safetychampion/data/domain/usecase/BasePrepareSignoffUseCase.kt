package au.com.safetychampion.data.domain.usecase

import au.com.safetychampion.data.data.common.TaskDAO
import au.com.safetychampion.data.domain.core.* // ktlint-disable no-wildcard-imports
import au.com.safetychampion.data.domain.manager.IOfflineConverter
import au.com.safetychampion.data.util.extension.koinInject

/**
 * A base use-case with caching implementation to provide data for sign-off feature,
 * also save data to database whenever we fetch it from remote datasource
 **/

abstract class BasePrepareSignoffUseCase<T> : BaseUseCase() {

    protected val offlineTaskRepo: TaskDAO by koinInject()

    protected val offlineTaskConverter: IOfflineConverter by koinInject()

    /**
     * @return true if this task was closed, false otherwise
     */
    protected open fun closedCheck(it: T?): Boolean = false

    protected open fun fromDatabase(): T {
        TODO()
    }

    protected open fun insertToDatabase(data: T?) {
    }

    protected suspend inline fun fromOfflineTaskFirst(
        offlineTaskId: String?,
        crossinline remote: suspend () -> Result<T>
    ): Result<T> {
        val offData: T? = offlineTaskConverter.toObject(
            offlineTask = offlineTaskRepo.getOfflineTask(offlineTaskId)
        )
        if (offData != null) {
            return Result.Success(offData)
        }

        return remote.invoke()
            .flatMap {
                insertToDatabase(it)
                if (closedCheck(it)) {
                    Result.Error(SCError.AlreadySignedOff)
                } else {
                    Result.Success(it)
                }
            }
            .flatMapError {
                if (it is SCError.NoNetwork) {
                    Result.Success(fromDatabase())
                } else {
                    Result.Error(it)
                }
            }
    }
}
