package au.com.safetychampion.data.data.incident
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.hr.HrLookupItem
import au.com.safetychampion.data.domain.models.incidents.* // ktlint-disable no-wildcard-imports
import au.com.safetychampion.scmobile.modules.incident.model.ConfiguredLocations

interface IIncidentRepository {
    suspend fun list(): Result<List<Incident>>
    suspend fun new(body: IncidentNewPL): Result<Incident>
    suspend fun fetchIncident(taskID: String): Result<Incident>
    suspend fun fetchTask(taskID: String): Result<IncidentTask>
    suspend fun fetchConfigLocation(): Result<ConfiguredLocations>
    suspend fun signOff(payload: IncidentTaskPL): Result<IncidentTask>
    suspend fun hrLookUp(): Result<List<HrLookupItem>>
    suspend fun combineFetchAndTask(
        taskId: String,
        moduleId: String
    ): Result<IncidentSignOff>
}
