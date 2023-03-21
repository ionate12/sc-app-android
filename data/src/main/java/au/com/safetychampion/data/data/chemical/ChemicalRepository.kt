package au.com.safetychampion.data.data.chemical

import au.com.safetychampion.data.data.BaseRepository
import au.com.safetychampion.data.data.api.ChemicalAPI
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.core.map
import au.com.safetychampion.data.domain.models.chemical.*

class ChemicalRepository : BaseRepository(), IChemicalRepository {

    override suspend fun list(): Result<List<Chemical>> {
        return ChemicalAPI.List().call()
    }

    override suspend fun ghsCode(): Result<List<GHSCode>> {
        return ChemicalAPI.ListCode().call()
    }

    override suspend fun fetch(moduleId: String): Result<Chemical> {
        return ChemicalAPI.Fetch(moduleId).call()
    }

    override suspend fun combineFetchAndTask(moduleId: String, taskId: String): Result<ChemicalSignoffPL> {
        return fetch(moduleId).map { ChemicalSignoffPL(body = it, task = ChemicalTask(_id = taskId)) }
    }

    override suspend fun signoff(
        chemId: String,
        taskId: String,
        payload: ChemicalTaskPL
    ): Result<ChemicalTask> {
        return ChemicalAPI.Signoff(
            moduleId = chemId,
            taskId = taskId,
            body = payload
        ).call()
    }
}
