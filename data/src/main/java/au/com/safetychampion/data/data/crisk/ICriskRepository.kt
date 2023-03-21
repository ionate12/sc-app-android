package au.com.safetychampion.data.data.crisk

import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.contractor.ContractorLookup
import au.com.safetychampion.data.domain.models.crisk.Crisk
import au.com.safetychampion.data.domain.models.crisk.CriskArchivePayload
import au.com.safetychampion.data.domain.models.crisk.CriskSignoff
import au.com.safetychampion.data.domain.models.crisk.CriskTask
import au.com.safetychampion.data.domain.models.crisk.CriskTaskPL
import au.com.safetychampion.data.domain.models.hr.HrLookupItem
import au.com.safetychampion.data.domain.models.task.UpdateLogListItem
import com.google.gson.JsonObject

interface ICriskRepository {
    suspend fun list(): Result<List<Crisk>>
    suspend fun hrLookup(): Result<List<HrLookupItem>>
    suspend fun contractorLookup(): Result<List<ContractorLookup>>
    suspend fun fetch(criskId: String): Result<Crisk>
    suspend fun task(criskId: String, taskId: String): Result<CriskTask>
    suspend fun evidences(criskId: String): Result<List<UpdateLogListItem>>
    suspend fun archive(criskId: String, payload: CriskArchivePayload): Result<JsonObject> // TODO()
    suspend fun combineFetchAndTask(taskId: String, criskId: String): Result<CriskSignoff>
    suspend fun signoff(criskId: String, taskId: String, payload: CriskTaskPL): Result<CriskTask>
}
