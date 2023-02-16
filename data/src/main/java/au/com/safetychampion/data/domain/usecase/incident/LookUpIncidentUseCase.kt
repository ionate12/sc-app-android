package au.com.safetychampion.data.domain.usecase.incident

import au.com.safetychampion.data.data.incident.IIncidentRepository
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.hr.HrLookupItem

class LookUpIncidentUseCase(
    private val repository: IIncidentRepository
) {
    suspend fun invoke(): Result<List<HrLookupItem>> {
        return repository.hrLookUp()
    }
}
