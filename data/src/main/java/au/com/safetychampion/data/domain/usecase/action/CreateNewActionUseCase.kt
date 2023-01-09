package au.com.safetychampion.data.domain.usecase.action

import au.com.safetychampion.data.data.action.ActionRepository
import au.com.safetychampion.data.domain.Attachment
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.action.payload.Action

class CreateNewActionUseCase(
    private val repository: ActionRepository
) {
    suspend operator fun invoke(
        payload: Action,
        attachments: List<Attachment>
    ): Result<Action> {
        return repository.createNewAction(payload = payload, attachments = attachments)
    }
}
