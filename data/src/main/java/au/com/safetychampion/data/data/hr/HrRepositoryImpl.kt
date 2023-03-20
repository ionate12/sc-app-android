
package au.com.safetychampion.data.data.hr

import au.com.safetychampion.data.data.BaseRepository
import au.com.safetychampion.data.data.api.HrAPI
import au.com.safetychampion.data.domain.models.hr.HRProfile
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.incidents.LinkedIncident

class HrRepositoryImpl : BaseRepository(), IHrRepository {
    override suspend fun list(): Result<List<HRProfile>>{
        return HrAPI.List().call()
    }

    override suspend fun fetch(moduleId: String): Result<HRProfile> {
        return HrAPI.Fetch(moduleId).call()
    }

    override suspend fun listLinkedIncidents(moduleId: String): Result<List<LinkedIncident>> {
        return HrAPI.ListLinkedIncidents(moduleId).call()
    }
}