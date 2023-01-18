package au.com.safetychampion.data.domain.usecase.action

import au.com.safetychampion.data.data.action.IActionRepository
import au.com.safetychampion.data.domain.Attachment
import au.com.safetychampion.data.domain.models.action.ActionLink
import au.com.safetychampion.data.domain.models.action.network.ActionPL
import au.com.safetychampion.data.domain.core.Result

class CreatePendingActionUseCase(
    private val repository: IActionRepository
) {
    suspend operator fun invoke(
        payload: ActionPL,
        attachments: List<Attachment>
    ): Result<ActionLink?> {
        return repository.createPendingAction(payload = payload, attachments = attachments)
    }
}
