package au.com.safetychampion.data.domain.usecase.action

import au.com.safetychampion.data.data.action.ActionRepository
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.action.payload.Action
import au.com.safetychampion.data.domain.models.action.payload.ActionSignOff

class SignOffActionUseCase(
    private val repository: ActionRepository
) {
    suspend operator fun invoke(
        actionId: String,
        taskId: String,
        actionPL: Action,
        signOffPL: ActionSignOff

    ): Result<List<Action>> {
        TODO()
//        repository.signOff(
//            actionId = actionId,
//            payload =
//        )
    }
}
