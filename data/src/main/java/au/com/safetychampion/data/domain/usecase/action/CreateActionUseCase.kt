package au.com.safetychampion.data.domain.usecase.action

import au.com.safetychampion.data.data.action.IActionRepository
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.action.Action
import au.com.safetychampion.data.domain.models.action.network.ActionPL

class CreateActionUseCase(
    private val repository: IActionRepository
) {
    suspend operator fun invoke(
        payload: ActionPL
    ): Result<Action> {
        return repository.createAction(payload = payload)
    }
}
