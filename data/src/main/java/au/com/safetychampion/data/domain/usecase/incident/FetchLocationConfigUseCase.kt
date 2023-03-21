package au.com.safetychampion.data.domain.usecase.incident

import au.com.safetychampion.data.data.incident.IIncidentRepository
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.scmobile.modules.incident.model.ConfiguredLocations

class FetchLocationConfigUseCase(
    private val repository: IIncidentRepository
) {

    suspend fun invoke(): Result<ConfiguredLocations> {
        return repository.fetchConfigLocation()
    }
}
