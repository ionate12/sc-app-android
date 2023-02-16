package au.com.safetychampion.data.data.incident

import au.com.safetychampion.data.data.BaseRepository
import au.com.safetychampion.data.data.api.IncidentAPI
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.core.dataOrNull
import au.com.safetychampion.data.domain.core.map
import au.com.safetychampion.data.domain.models.hr.HrLookupItem
import au.com.safetychampion.data.domain.models.incidents.* // ktlint-disable no-wildcard-imports
import au.com.safetychampion.scmobile.modules.incident.model.ConfiguredLocations
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class IncidentRepositoryImpl : BaseRepository(), IIncidentRepository {
    override suspend fun list(): Result<List<Incident>> {
        return IncidentAPI.List().call()
    }

    override suspend fun new(body: IncidentNewPL): Result<Incident> {
        return IncidentAPI.New(body).call()
    }

    override suspend fun fetchIncident(taskID: String): Result<Incident> {
        return IncidentAPI.Fetch(taskID).call()
    }

    override suspend fun fetchTask(taskID: String): Result<IncidentTask> {
        return IncidentAPI.Task(taskID).call()
    }

    override suspend fun fetchConfigLocation(): Result<ConfiguredLocations> {
        return IncidentAPI.ConfigLocations().call()
    }

    override suspend fun signOff(
        payload: IncidentTaskPL
    ): Result<IncidentTask> {
        return IncidentAPI.Signoff(
            payload = payload,
            incidentID = payload.taskId
        ).call()
    }

    override suspend fun hrLookUp(): Result<List<HrLookupItem>> {
        return IncidentAPI.HRLookup()
            .call<List<List<String>>>()
            .map { list ->
                list.map {
                    val item = HrLookupItem()
                    item.import(it)
                    item
                }
            }
    }

    @Throws(IllegalArgumentException::class)
    override suspend fun combineFetchAndTask(
        taskId: String,
        moduleId: String
    ): Result<IncidentSignOff> {
        return withContext(dispatchers.io) {
            val fetchCall = async { fetchIncident(moduleId) }
            val taskCall = async { fetchTask(taskId) }

            val fetchData = fetchCall.await().dataOrNull()
            val taskData = taskCall.await().dataOrNull()
            requireNotNull(fetchData)
            requireNotNull(taskData)
            return@withContext Result.Success(
                IncidentSignOff(
                    body = fetchData,
                    task = taskData
                )
            )
        }
    }
}
