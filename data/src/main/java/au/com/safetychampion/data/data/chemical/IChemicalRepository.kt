package au.com.safetychampion.data.data.chemical

import au.com.safetychampion.data.domain.Attachment
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.GHSCode
import au.com.safetychampion.data.domain.models.SignoffStatus
import au.com.safetychampion.data.domain.models.chemical.Chemical
import au.com.safetychampion.data.domain.models.chemical.ChemicalSignoff
import au.com.safetychampion.data.domain.models.chemical.ChemicalTask
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow

interface IChemicalRepository {
    suspend fun refreshChemicalList(): Job
    val chemicalList: Flow<List<Chemical>>

    suspend fun refreshGHSCodeList(): Job
    val GHSCode: Flow<GHSCode>

    val latestChemicalData: Flow<Pair<List<Chemical>, GHSCode>>

    suspend fun fetch(moduleId: String): Result<Chemical>

    suspend fun combineFetchAndTask(moduleId: String, taskId: String): Result<ChemicalSignoff>

    suspend fun signOff(
        moduleId: String,
        taskId: String,
        body: ChemicalTask,
        photos: List<Attachment>
    ): Result<SignoffStatus.OnlineCompleted>

    suspend fun save(
        moduleId: String,
        taskId: String,
        body: ChemicalTask,
        photos: List<Attachment>
    ): Result<SignoffStatus.OnlineSaved>
}
