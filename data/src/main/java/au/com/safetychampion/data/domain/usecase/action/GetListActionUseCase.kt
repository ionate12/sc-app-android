package au.com.safetychampion.data.domain.usecase.action

import au.com.safetychampion.data.data.action.ActionRepository
import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.action.ActionPojo

class GetListActionUseCase(
    private val repository: ActionRepository
) {
    suspend operator fun invoke(): Result<List<ActionPojo>> {
        return repository.list(body = object : BasePL() {})
    }
}
