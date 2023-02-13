import au.com.safetychampion.data.data.action.IActionRepository
import au.com.safetychampion.data.domain.base.BaseSignOff
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.action.ActionTask
import au.com.safetychampion.data.domain.models.action.network.ActionSignOff
import au.com.safetychampion.data.domain.usecase.BasePrepareSignoffUseCase
import au.com.safetychampion.data.util.extension.koinInject

class GetActionSignOffDetailsUseCase : BasePrepareSignoffUseCase<ActionTask, ActionSignOff>() {
    private val repo: IActionRepository by koinInject()

    override suspend fun fetchData(moduleId: String, taskId: String?): Result<ActionSignOff> {
        return repo.combineFetchAndTask(moduleId)
    }

    override fun getSyncableKey(moduleId: String, taskId: String?): String {
        return BaseSignOff.actionSignoffSyncableKey(moduleId)
    }
}
