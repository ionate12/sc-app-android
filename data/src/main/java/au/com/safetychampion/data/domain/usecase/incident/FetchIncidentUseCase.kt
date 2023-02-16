package au.com.safetychampion.data.domain.usecase.incident

import au.com.safetychampion.data.data.incident.IIncidentRepository
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.incidents.Incident

class FetchIncidentUseCase(
    private val repository: IIncidentRepository
) {
    suspend fun invoke(moduleId: String): Result<Incident> {
        return repository.fetchIncident(moduleId)
    }
}
