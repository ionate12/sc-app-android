package au.com.safetychampion.data.data.chemical

import au.com.safetychampion.data.data.BaseRepository
import au.com.safetychampion.data.data.common.MasterDAO
import au.com.safetychampion.data.domain.Attachment
import au.com.safetychampion.data.domain.core.* // ktlint-disable no-wildcard-imports
import au.com.safetychampion.data.domain.models.GHSCode
import au.com.safetychampion.data.domain.models.SignoffStatus
import au.com.safetychampion.data.domain.models.chemical.Chemical
import au.com.safetychampion.data.domain.models.chemical.ChemicalSignoff
import au.com.safetychampion.data.domain.models.chemical.ChemicalTask
import au.com.safetychampion.data.domain.toMultipartBody
import au.com.safetychampion.data.domain.uncategory.setFilePath
import au.com.safetychampion.util.koinInject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class ChemicalRepositoryImpl : BaseRepository(), IChemicalRepository {

    private val api: ChemicalAPI by koinInject()

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
                apiCallAsList<Chemical> { api.listChemicals() }
                    .doOnSucceed {
                        it.forEach { chem ->
                            chem.attachments?.setFilePath(
                                filePath = fileContentManager.externalFilesDir
                            )
                            chem.setWPName("abc") // TODO("WPName")
                        }
                        masterRepo.insertToDB(it)
                        Timber.tag(TAG).d("refresh Chemicals: $it")
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
                apiCall<GHSCode> { api.listCodes() }
                    .doOnSucceed {
                        Timber.tag(TAG).d("refresh GHS: $it")
                        masterRepo.insertToDB(it)
                    }
            }
        }
    }

    override suspend fun fetch(moduleId: String): Result<Chemical> {
        return apiCall { api.fetch(moduleId) }
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

    override suspend fun signOff(
        moduleId: String,
        taskId: String,
        body: ChemicalTask,
        photos: List<Attachment>
    ): Result<SignoffStatus.OnlineCompleted> {
        return apiCall<SignoffStatus.OnlineCompleted> {
            api.signOff(
                moduleId = moduleId,
                taskId = taskId,
                body = body.toRequestBody(),
                photos = photos.toMultipartBody(
                    fileManager = fileContentManager
                )
            )
        }.doOnSucceed {
            it.moduleName = ModuleName.CHEMICAL.name
            it.title = "titleABC"
        }
    }

    override suspend fun save(
        moduleId: String,
        taskId: String,
        body: ChemicalTask,
        photos: List<Attachment>
    ): Result<SignoffStatus.OnlineSaved> {
        return apiCall<SignoffStatus.OnlineSaved> {
            api.signOff(
                moduleId = moduleId,
                taskId = taskId,
                body = body.toRequestBody(),
                photos = photos.toMultipartBody(
                    fileManager = fileContentManager
                )
            )
        }.doOnSucceed {
            it.moduleName = ModuleName.CHEMICAL.name
            it.title = "titleABC"
        }
    }
}
