package au.com.safetychampion.data.domain.usecase.action

import au.com.safetychampion.data.data.action.ActionRepository
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.action.ActionPojo
import au.com.safetychampion.data.domain.models.action.ActionSignOffPL

class SignOffActionUseCase(
    private val repository: ActionRepository
) {
    suspend operator fun invoke(
        actionId: String,
        taskId: String,
        actionPL: ActionPojo,
        signOffPL: ActionSignOffPL

    ): Result<List<ActionPojo>> {
        TODO()
//        repository.signOff(
//            actionId = actionId,
//            payload =
//        )
    }
}
