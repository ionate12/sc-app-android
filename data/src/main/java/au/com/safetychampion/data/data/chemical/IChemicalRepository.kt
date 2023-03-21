package au.com.safetychampion.data.data.chemical

import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.chemical.*

interface IChemicalRepository {

    suspend fun list(): Result<List<Chemical>>

    suspend fun ghsCode(): Result<List<GHSCode>>

    suspend fun fetch(moduleId: String): Result<Chemical>

    suspend fun combineFetchAndTask(moduleId: String, taskId: String): Result<ChemicalSignoffPL>

    suspend fun signoff(
        chemId: String,
        taskId: String,
        payload: ChemicalTaskPL
    ): Result<ChemicalTask>
}
