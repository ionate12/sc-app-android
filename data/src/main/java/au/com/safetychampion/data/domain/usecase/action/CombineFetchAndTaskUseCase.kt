package au.com.safetychampion.data.domain.usecase.action

import au.com.safetychampion.data.data.action.IActionRepository
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.core.SCError
import au.com.safetychampion.data.domain.core.dataOrNull
import au.com.safetychampion.data.domain.core.errorOrNull
import au.com.safetychampion.data.domain.models.action.network.ActionSignOffPL
import au.com.safetychampion.data.domain.usecase.BaseUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class CombineFetchAndTaskUseCase(
    private val repository: IActionRepository
) : BaseUseCase() {
    suspend operator fun invoke(actionID: String): Result<ActionSignOffPL> {
        return withContext(Dispatchers.IO) {
            val actionFetch = async { repository.fetchAction(actionID) }
            val taskFetch = async { repository.task(actionID) }

            val fetch = actionFetch.await()
            val task = taskFetch.await()

            return@withContext when {
                fetch.errorOrNull() is SCError.NoNetwork || task.errorOrNull() is SCError.NoNetwork -> Result.Error(
                    SCError.NoNetwork
                )
                fetch is Result.Error || task is Result.Error -> { Result.Error(err = fetch.errorOrNull() ?: task.errorOrNull()!!) }
                else -> {
                    val _task = task.dataOrNull()!!
                    val _fetch = fetch.dataOrNull()!!
                    Result.Success(
                        data = ActionSignOffPL(
                            _id = _task._id,
                            moduleId = _fetch._id,
                            body = _fetch,
                            task = _task
                        )
                    )
                }
            }
        }
    }
}
