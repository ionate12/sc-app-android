import au.com.safetychampion.data.data.action.IActionRepository
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.action.network.ActionSignOff
import au.com.safetychampion.data.domain.usecase.BasePrepareSignoffUseCase
import au.com.safetychampion.data.util.extension.koinInject

class GetActionSignOffDetailsUseCase() : BasePrepareSignoffUseCase<ActionSignOff>() {
    private val repo: IActionRepository by koinInject()

    suspend operator fun invoke(
        id: String,
        actionID: String
    ): Result<ActionSignOff> {
        return fromOfflineTaskFirst(
            offlineTaskId = id,
            remote = { repo.combineFetchAndTask(actionID) }
        )
    }

    override fun closedCheck(it: ActionSignOff?): Boolean {
        return it?.body?.closed ?: false
    }
}
