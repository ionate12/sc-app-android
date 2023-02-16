package au.com.safetychampion.data.domain.usecase.incident

import au.com.safetychampion.data.data.incident.IIncidentRepository
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.incidents.IncidentSignOff
import au.com.safetychampion.data.domain.models.incidents.IncidentTask
import au.com.safetychampion.data.domain.models.incidents.IncidentTaskPL
import au.com.safetychampion.data.domain.usecase.BaseSignoffUseCase
import au.com.safetychampion.data.util.extension.koinInject

class SignoffIncidentUseCase : BaseSignoffUseCase<IncidentTask, IncidentSignOff>() {
    private val repository: IIncidentRepository by koinInject()
    override suspend fun signoffOnline(payload: IncidentSignOff): Result<IncidentTask> {
        return repository.signOff(IncidentTaskPL.from(payload.task))
    }
}
