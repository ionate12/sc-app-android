import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.action.network.ActionSignOffPL
import au.com.safetychampion.data.domain.models.task.Task
import au.com.safetychampion.data.domain.usecase.BasePrepareSignoffUseCase
import au.com.safetychampion.data.domain.usecase.action.CombineFetchAndTaskUseCase
import au.com.safetychampion.util.koinInject

class GetActionSignOffDetailsUseCase() : BasePrepareSignoffUseCase<ActionSignOffPL>() {
    private val combineTaskAndFetchUseCase: CombineFetchAndTaskUseCase by koinInject()

    suspend operator fun invoke(
        task: Task,
        actionID: String
    ): Result<ActionSignOffPL> {
        return fromOfflineTaskFirst(
            offlineTaskId = task._id,
            remote = { combineTaskAndFetchUseCase.invoke(actionID) },
            local = { fromDatabase() }
        )
    }

    override fun closedCheck(it: ActionSignOffPL?): Boolean {
        return it?.body?.closed ?: false
    }
}
