package au.com.safetychampion.data.domain.usecase.action

import au.com.safetychampion.data.data.action.IActionRepository
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.core.map
import au.com.safetychampion.data.domain.models.action.ActionLink
import au.com.safetychampion.data.domain.models.action.network.ActionPL
import au.com.safetychampion.data.domain.models.action.network.PendingActionPL
import au.com.safetychampion.data.domain.usecase.BaseUseCase
import au.com.safetychampion.data.util.extension.koinInject
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

class CreatePendingActionUseCase : BaseUseCase() {
    private val repository: IActionRepository by koinInject()
    suspend operator fun invoke(
        payload: ActionPL
    ): Result<ActionLink> {
        return repository.createAction(payload = payload)
            .map { it.toActionLink() }
    }
}

class CreateMultiPendingActionsUseCase : BaseUseCase() {
    private val createPendingActionUseCase: CreatePendingActionUseCase by koinInject()
    suspend operator fun invoke(pendingActionPL: List<PendingActionPL>): List<Result<ActionLink>> {
        if (pendingActionPL.isEmpty()) return listOf()
        return withContext(dispatchers.io) {
            pendingActionPL.map {
                async { createPendingActionUseCase(it.action) }
            }.awaitAll()
        }
    }
}
