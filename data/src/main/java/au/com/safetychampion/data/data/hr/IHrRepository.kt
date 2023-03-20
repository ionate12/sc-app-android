package au.com.safetychampion.data.data.hr

import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.hr.HRProfile
import au.com.safetychampion.data.domain.models.incidents.LinkedIncident

interface IHrRepository {
    suspend fun fetch(moduleId: String): Result<HRProfile>
    suspend fun list(): Result<List<HRProfile>>
    suspend fun listLinkedIncidents(moduleId: String): Result<List<LinkedIncident>>
}
