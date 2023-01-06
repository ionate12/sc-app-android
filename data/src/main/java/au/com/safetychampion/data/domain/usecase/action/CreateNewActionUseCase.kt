package au.com.safetychampion.data.domain.usecase.action

import au.com.safetychampion.data.data.action.ActionRepository
import au.com.safetychampion.data.domain.Attachment
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.payload.ActionPojo

class CreateNewActionUseCase(
    private val repository: ActionRepository
) {
    suspend operator fun invoke(
        payload: ActionPojo,
        attachments: List<Attachment>
    ): Result<ActionPojo> {
        return repository.createNewAction(payload = payload, attachments = attachments)
    }
}
