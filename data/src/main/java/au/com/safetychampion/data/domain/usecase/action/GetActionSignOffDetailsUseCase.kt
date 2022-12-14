package au.com.safetychampion.data.domain.usecase.action

import au.com.safetychampion.data.data.action.IActionRepository
import au.com.safetychampion.data.domain.core.SCError
import au.com.safetychampion.data.domain.core.dataOrNull
import au.com.safetychampion.data.domain.models.action.payload.ActionSignOffPL
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class GetActionSignOffDetailsUseCase(
    private val repository: IActionRepository
) {
    suspend operator fun invoke(
        actionID: String
    ): au.com.safetychampion.data.domain.core.Result<ActionSignOffPL> {
        return withContext(Dispatchers.IO) {
            val actionFetch = async { repository.fetchAction(actionID) }
            val taskFetch = async { repository.task(actionID) }

            val body = actionFetch.toItemOrNull()
            val task = taskFetch.toItemOrNull()

            return@withContext if (body != null && task != null) {
                au.com.safetychampion.data.domain.core.Result.Success(
                    ActionSignOffPL(
                        _id = task._id,
                        moduleId = body._id,
                        body = body,
                        task = task
                    )
                )
            } else {
                au.com.safetychampion.data.domain.core.Result.Error(SCError.EmptyResult)
            }
        }
    }
}

suspend inline fun <reified T> Deferred<au.com.safetychampion.data.domain.core.Result<T>>.toItemOrNull(): T? {
    return await().dataOrNull()
}
