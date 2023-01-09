package au.com.safetychampion.data.domain.usecase.action

import au.com.safetychampion.data.data.action.IActionRepository
import au.com.safetychampion.data.domain.Attachment
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.action.payload.ActionPL

class CreateNewActionUseCase(
    private val repository: IActionRepository
) {
    suspend operator fun invoke(
        payload: ActionPL,
        attachments: List<Attachment>
    ): Result<ActionPL> {
        return repository.createNewAction(payload = payload, attachments = attachments)
    }
}
