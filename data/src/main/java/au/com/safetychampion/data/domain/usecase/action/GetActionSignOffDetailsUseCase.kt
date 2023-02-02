import au.com.safetychampion.data.data.action.IActionRepository
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.action.network.ActionSignOffPL
import au.com.safetychampion.data.domain.usecase.BasePrepareSignoffUseCase
import au.com.safetychampion.data.util.extension.koinInject

class GetActionSignOffDetailsUseCase() : BasePrepareSignoffUseCase<ActionSignOffPL>() {
    private val repo: IActionRepository by koinInject()

    suspend operator fun invoke(
        id: String,
        actionID: String
    ): Result<ActionSignOffPL> {
        return fromOfflineTaskFirst(
            offlineTaskId = id,
            remote = { repo.combineFetchAndTask(actionID) }
        )
    }

    override fun closedCheck(it: ActionSignOffPL?): Boolean {
        return it?.body?.closed ?: false
    }
}
