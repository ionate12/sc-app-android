package au.com.safetychampion.data.domain.usecase.action

import au.com.safetychampion.data.data.action.IActionRepository
import au.com.safetychampion.data.domain.Attachment
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.core.dataOrNull
import au.com.safetychampion.data.domain.models.action.ActionLink
import au.com.safetychampion.data.domain.models.action.network.ActionPL
import au.com.safetychampion.data.domain.models.action.network.PendingActionPL
import au.com.safetychampion.data.domain.usecase.BaseUseCase
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

class CreatePendingActionUseCase(
    private val repository: IActionRepository
) : BaseUseCase() {
    suspend operator fun invoke(
        payload: ActionPL,
        attachments: List<Attachment>
    ): Result<ActionLink?> {
        return repository.createPendingAction(payload = payload, attachments = attachments)
    }
}

class CreatePendingActionAsynchronousUseCase(
    private val repository: IActionRepository
) : BaseUseCase() {
    suspend operator fun invoke(pendingActionPL: List<PendingActionPL>?): Result.Success<List<ActionLink>> {
        if (pendingActionPL == null) {
            return Result.Success(emptyList())
        }

        return withContext(dispatchers.io) {
            Result.Success(
                pendingActionPL.map {
                    async(SupervisorJob()) {
                        repository.createPendingAction(
                            payload = it.action,
                            attachments = it.attachments
                        ).dataOrNull()
                    }
                }
                    .awaitAll()
                    .filterNotNull()
            )
        }
    }
}
