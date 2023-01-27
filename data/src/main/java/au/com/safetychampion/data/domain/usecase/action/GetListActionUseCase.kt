package au.com.safetychampion.data.domain.usecase.action

import au.com.safetychampion.data.data.action.IActionRepository
import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.action.network.ActionPL

class GetListActionUseCase(
    private val repository: IActionRepository
) {
    suspend operator fun invoke(): Result<List<ActionPL>> {
        return repository.list(body = BasePL.empty())
    }
}
