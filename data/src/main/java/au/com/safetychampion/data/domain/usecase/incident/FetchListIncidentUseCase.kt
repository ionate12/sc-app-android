package au.com.safetychampion.data.domain.usecase.incident

import au.com.safetychampion.data.data.incident.IIncidentRepository
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.incidents.Incident

class FetchListIncidentUseCase(
    private val repository: IIncidentRepository
) {
    suspend operator fun invoke(): Result<List<Incident>> {
        return repository.list()
    }
}
