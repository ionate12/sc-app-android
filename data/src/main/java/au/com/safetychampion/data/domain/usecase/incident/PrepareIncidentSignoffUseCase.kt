package au.com.safetychampion.data.domain.usecase.incident

import au.com.safetychampion.data.data.incident.IIncidentRepository
import au.com.safetychampion.data.domain.base.BaseSignOff
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.incidents.IncidentSignOff
import au.com.safetychampion.data.domain.models.incidents.IncidentTask
import au.com.safetychampion.data.domain.usecase.BasePrepareSignoffUseCase
import au.com.safetychampion.data.util.extension.koinInject

class PrepareIncidentSignoffUseCase : BasePrepareSignoffUseCase<IncidentTask, IncidentSignOff>() {
    private val repository: IIncidentRepository by koinInject()

    override suspend fun fetchData(moduleId: String, taskId: String?): Result<IncidentSignOff> {
        requireNotNull(taskId)
        return repository.combineFetchAndTask(taskId, moduleId)
    }

    override fun getSyncableKey(moduleId: String, taskId: String?): String {
        requireNotNull(taskId)
        return BaseSignOff.incidentSignoffSyncableKey(taskId)
    }
}
