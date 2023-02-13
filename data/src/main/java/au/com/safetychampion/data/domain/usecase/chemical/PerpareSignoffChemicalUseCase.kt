package au.com.safetychampion.data.domain.usecase.chemical

import au.com.safetychampion.data.data.chemical.IChemicalRepository
import au.com.safetychampion.data.domain.base.BaseSignOff
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.chemical.ChemicalSignoff
import au.com.safetychampion.data.domain.models.chemical.ChemicalTask
import au.com.safetychampion.data.domain.usecase.BasePrepareSignoffUseCase
import au.com.safetychampion.data.util.extension.koinInject

class PerpareSignoffChemicalUseCase : BasePrepareSignoffUseCase<ChemicalTask, ChemicalSignoff>() {

    private val repo: IChemicalRepository by koinInject()

    override suspend fun fetchData(moduleId: String, taskId: String?): Result<ChemicalSignoff> {
        requireNotNull(taskId)
        return repo.combineFetchAndTask(moduleId, taskId)
    }

    override fun getSyncableKey(moduleId: String, taskId: String?): String {
        requireNotNull(taskId)
        return BaseSignOff.chemicalSignoffSyncableKey(moduleId, taskId)
    }
}
