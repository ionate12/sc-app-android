package au.com.safetychampion.data.domain.usecase.action

import au.com.safetychampion.data.data.action.IActionRepository
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.action.payload.ActionPL
import au.com.safetychampion.data.domain.models.action.payload.ActionSignOffPL

class SignOffActionUseCase(
    private val repository: IActionRepository
) {
    suspend operator fun invoke(
        actionId: String,
        taskId: String,
        actionPL: ActionPL,
        signOffPL: ActionSignOffPL

    ): Result<List<ActionPL>> {
        TODO()
//        repository.signOff(
//            actionId = actionId,
//            payload =
//        )
    }
}
