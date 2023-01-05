package au.com.safetychampion.data.domain.usecase.action

import au.com.safetychampion.data.data.action.ActionRepository
import au.com.safetychampion.data.domain.Attachment
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.payload.ActionPojo

class EditActionUseCase(
    private val repository: ActionRepository
) {
    suspend operator fun invoke(
        taskId: String,
        payload: ActionPojo,
        attachments: List<Attachment>
    ): Result<Unit> {
        return repository.editAction(
            taskId = taskId,
            payload = payload,
            attachments = attachments
        )
    }
}
