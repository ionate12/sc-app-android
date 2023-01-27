package au.com.safetychampion.data.domain.usecase

import au.com.safetychampion.data.data.common.TaskDAO
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.core.SCError
import au.com.safetychampion.data.domain.core.flatMapError
import au.com.safetychampion.data.domain.manager.IOfflineConverter
import au.com.safetychampion.data.domain.models.SignoffStatus
import au.com.safetychampion.data.domain.usecase.action.OfflineTask
import au.com.safetychampion.data.domain.usecase.action.OfflineTaskInfo
import au.com.safetychampion.util.koinInject

abstract class BaseSignoffUseCase<T : OfflineTaskInfo> : BaseUseCase() {

    private val offlineTaskConverter by koinInject<IOfflineConverter>()

    private val taskDAO by koinInject<TaskDAO>()

    private fun newOfflineTask(data: T): OfflineTask {
        return offlineTaskConverter.toOfflineTask(data)
    }

    private fun overwrite(param: T): Result<SignoffStatus.OfflineCompleted>? {
        return taskDAO.getOfflineTask(
            taskId = param.id
        )?.let {
            taskDAO.insertOfflineTask(
                // TODO("overwrite here")
                it
            )
            return Result.Success(
                SignoffStatus.OfflineCompleted(
                    title = param.title,
                    moduleName = param.moduleName.value,
                    isOverwritten = true
                )
            )
        }
    }

    private suspend fun signOff(param: T): Result<SignoffStatus> {
        return signoffInternal(param)
            .flatMapError {
                if (it is SCError.NoNetwork) {
                    // No internet connection case
                    taskDAO.insertOfflineTask(
                        newOfflineTask(param)
                    )
                    return@flatMapError Result.Success(
                        SignoffStatus.OfflineCompleted(
                            title = param.title,
                            moduleName = param.moduleName.value,
                            isOverwritten = false
                        )
                    )
                }
                return@flatMapError Result.Error(
                    SCError.SignOffFailed(
                        title = param.title,
                        moduleName = param.moduleName.value,
                        details = it.toString()
                    )
                )
            }
    }

    protected abstract suspend fun signoffInternal(param: T): Result<SignoffStatus>

    suspend operator fun invoke(param: T): Result<SignoffStatus> {
        return overwrite(param) ?: signOff(param)
    }
}
