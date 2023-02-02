package au.com.safetychampion.data.data.chemical

import au.com.safetychampion.data.data.BaseRepository
import au.com.safetychampion.data.data.api.ChemicalAPI
import au.com.safetychampion.data.data.common.MasterDAO
import au.com.safetychampion.data.domain.core.* // ktlint-disable no-wildcard-imports
import au.com.safetychampion.data.domain.models.GHSCode
import au.com.safetychampion.data.domain.models.SignoffStatus
import au.com.safetychampion.data.domain.models.chemical.Chemical
import au.com.safetychampion.data.domain.models.chemical.ChemicalSignoff
import au.com.safetychampion.data.domain.models.chemical.ChemicalTask
import au.com.safetychampion.data.domain.uncategory.setFilePath
import au.com.safetychampion.data.domain.usecase.chemical.ChemicalSignoffParam
import au.com.safetychampion.data.util.extension.koinInject
import au.com.safetychampion.data.util.extension.toJsonString
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class ChemicalRepositoryImpl : BaseRepository(), IChemicalRepository {

    private val masterRepo: MasterDAO by koinInject()

    private val TAG = "chemical"

    /**
     * Expose a flow of chemicals since we can remotely fetch it in service, in viewModel..
     * so to make sure the data is latest, whenever we fetch data, we save it to database
     * and this flow will be trigger by Room automatically.
     */

    override val chemicalList: Flow<List<Chemical>> by lazy { masterRepo.getChemicalList() }

    /**
     * @see [chemicalList]
     */

    override val GHSCode: Flow<GHSCode> by lazy { masterRepo.getGHSCode() }

    override val latestChemicalData by lazy {
        chemicalList.combine(GHSCode) { chems, ghs ->
            // TODO()
            chems to ghs
        }
    }

    /**
     * Synchronize local chemicals with remote data
     */
    override suspend fun refreshChemicalList(): Job {
        return withContext(dispatchers.io) {
            return@withContext launch {
                ChemicalAPI.List()
                    .callAsList<Chemical>()
                    .doOnSucceed {
                        it.forEach { chem ->
                            chem.attachments?.setFilePath(
                                filePath = fileContentManager.externalFilesDir
                            )
                            chem.setWPName("abc") // TODO("WPName")
                        }
                        masterRepo.insertToDB(it)
                        Timber.tag(TAG).d(
                            "refresh Chemicals: ${
                            suspend { it.toJsonString() }
                            }"
                        )
                    }
            }
        }
    }

    /**
     * Synchronize local GHSCode with remote data
     */
    override suspend fun refreshGHSCodeList(): Job {
        return withContext(dispatchers.io) {
            return@withContext launch {
                ChemicalAPI.ListCode()
                    .callAsList<GHSCode>()
                    .doOnSucceed {
                        Timber.tag(TAG).d(
                            "refresh GHS: ${
                            suspend { it.toJsonString() }
                            }"
                        )
                        masterRepo.insertToDB(it)
                    }
            }
        }
    }

    override suspend fun fetch(moduleId: String): Result<Chemical> {
        return ChemicalAPI.Fetch(moduleId).call()
    }

    override suspend fun combineFetchAndTask(moduleId: String, taskId: String): Result<ChemicalSignoff> {
        return when (val fetch = fetch(moduleId)) {
            is Result.Error -> fetch
            else -> Result.Success(
                ChemicalSignoff(
                    body = fetch.dataOrNull()!!,
                    task = ChemicalTask(),
                    taskId = taskId,
                    moduleId = moduleId
                )
            )
        }
    }

    override suspend fun save(params: ChemicalSignoffParam): Result<SignoffStatus.OnlineSaved> {
        return ChemicalAPI.Signoff(
            moduleId = params.moduleID,
            taskId = params.id,
            body = params.payload,
            photos = params.attachmentList
        )
            .call<SignoffStatus.OnlineSaved>()
            .doOnSucceed {
                it.moduleName = ModuleName.CHEMICAL.name
                it.title = "titleABC"
            }
    }

    override suspend fun signoff(params: ChemicalSignoffParam): Result<SignoffStatus.OnlineCompleted> {
        return ChemicalAPI.Signoff(
            moduleId = params.moduleID,
            taskId = params.id,
            body = params.payload,
            photos = params.attachmentList
        )
            .call<SignoffStatus.OnlineCompleted>()
            .doOnSucceed {
                it.moduleName = ModuleName.CHEMICAL.name
                it.title = "titleABC"
            }
    }
}
