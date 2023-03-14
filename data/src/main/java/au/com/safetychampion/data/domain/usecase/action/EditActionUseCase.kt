package au.com.safetychampion.data.domain.usecase.action

import au.com.safetychampion.data.data.action.IActionRepository
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.action.Action
import au.com.safetychampion.data.domain.models.action.network.ActionNewPL

class EditActionUseCase(
    private val repository: IActionRepository
) {
    suspend operator fun invoke(
        actionId: String,
        payload: ActionNewPL
    ): Result<Action> {
        return repository.editAction(
            actionId = actionId,
            payload = payload
        )
    }
}
