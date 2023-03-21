package au.com.safetychampion.data.data.crisk

import au.com.safetychampion.data.data.BaseRepository
import au.com.safetychampion.data.data.api.CriskAPI
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.core.SCError
import au.com.safetychampion.data.domain.core.dataOrNull
import au.com.safetychampion.data.domain.core.errorOrNull
import au.com.safetychampion.data.domain.core.flatMap
import au.com.safetychampion.data.domain.models.contractor.ContractorLookup
import au.com.safetychampion.data.domain.models.crisk.Crisk
import au.com.safetychampion.data.domain.models.crisk.CriskArchivePayload
import au.com.safetychampion.data.domain.models.crisk.CriskEvidenceTask
import au.com.safetychampion.data.domain.models.crisk.CriskSignoff
import au.com.safetychampion.data.domain.models.crisk.CriskTask
import au.com.safetychampion.data.domain.models.crisk.CriskTaskPL
import au.com.safetychampion.data.domain.models.hr.HrLookupItem
import au.com.safetychampion.data.domain.models.task.UpdateLogListItem
import com.google.gson.JsonObject
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class CriskRepository : BaseRepository(), ICriskRepository {
    override suspend fun list(): Result<List<Crisk>> {
        return CriskAPI.List().call()
    }

    override suspend fun hrLookup(): Result<List<HrLookupItem>> {
        return CriskAPI.HrLookUp()
            .call<List<List<String>>>()
            .flatMap {
                Result.Success(
                    it.map(HrLookupItem::fromRawData)
                )
            }
    }

    override suspend fun contractorLookup(): Result<List<ContractorLookup>> {
        return CriskAPI.ContractorLookUp()
            .call<List<List<Any>>>("records")
            .flatMap {
                Result.Success(
                    it.map(ContractorLookup::fromRawData)
                )
            }
    }

    override suspend fun fetch(criskId: String): Result<Crisk> {
        return CriskAPI.Fetch(criskId).call()
    }

    override suspend fun task(criskId: String, taskId: String): Result<CriskTask> {
        return CriskAPI.Tasks(
            criskId = criskId,
            taskId = taskId
        ).call()
    }

    override suspend fun evidences(criskId: String): Result<List<UpdateLogListItem>> {
        return CriskAPI.Tasks(
            criskId = criskId
        ).call<List<CriskEvidenceTask>>()
            .flatMap {
                Result.Success(
                    it.map(
                        UpdateLogListItem::fromCriskEvidence
                    )
                )
            }
    }

    override suspend fun archive(
        criskId: String,
        payload: CriskArchivePayload
    ): Result<JsonObject> {
        return CriskAPI.Archive(
            criskId = criskId,
            body = payload
        ).call(objName = "")
    }

    override suspend fun combineFetchAndTask(
        taskId: String,
        criskId: String
    ): Result<CriskSignoff> {
        return withContext(dispatchers.io) {
            val actionFetch = async { fetch(criskId) }
            val taskFetch = async { task(criskId, taskId) }

            val fetch = actionFetch.await()
            val task = taskFetch.await()

            return@withContext when {
                fetch.errorOrNull() is SCError.NoNetwork || task.errorOrNull() is SCError.NoNetwork -> Result.Error(
                    SCError.NoNetwork
                )
                fetch is Result.Error || task is Result.Error -> { Result.Error(err = fetch.errorOrNull() ?: task.errorOrNull()!!) }
                else -> {
                    val _task = task.dataOrNull()!!
                    val _fetch = fetch.dataOrNull()!! // TODO("this is notnull, fix bug in cusval first")
                    Result.Success(
                        data = CriskSignoff(
                            body = _fetch,
                            task = _task
                        )
                    )
                }
            }
        }
    }

    override suspend fun signoff(
        criskId: String,
        taskId: String,
        payload: CriskTaskPL
    ): Result<CriskTask> {
        return CriskAPI.Signoff(criskId, taskId, payload).call()
    }
}
