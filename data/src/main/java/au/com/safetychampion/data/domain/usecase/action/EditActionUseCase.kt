package au.com.safetychampion.data.domain.usecase.action

import au.com.safetychampion.data.data.action.IActionRepository
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.action.Action
import au.com.safetychampion.data.domain.models.action.network.ActionPL

class EditActionUseCase(
    private val repository: IActionRepository
) {
    suspend operator fun invoke(
        actionId: String,
        payload: ActionPL
    ): Result<Action> {
        return repository.editAction(
            actionId = actionId,
            payload = payload
        )
    }
}
