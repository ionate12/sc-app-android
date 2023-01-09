package au.com.safetychampion.data.domain.usecase.action

import au.com.safetychampion.data.data.action.IActionRepository
import au.com.safetychampion.data.domain.Attachment
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.action.payload.ActionPL

class EditActionUseCase(
    private val repository: IActionRepository
) {
    suspend operator fun invoke(
        taskId: String,
        payload: ActionPL,
        attachments: List<Attachment>
    ): Result<Unit> {
        return repository.editAction(
            taskId = taskId,
            payload = payload,
            attachments = attachments
        )
    }
}
