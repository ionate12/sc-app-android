package au.com.safetychampion.data.domain.usecase.chemical

import au.com.safetychampion.data.data.chemical.IChemicalRepository
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.chemical.ChemicalSignoff
import au.com.safetychampion.data.domain.usecase.BasePrepareSignoffUseCase
import au.com.safetychampion.data.util.extension.koinInject

class GetChemicalSignoffDetailUseCase : BasePrepareSignoffUseCase<ChemicalSignoff>() {

    private val repo: IChemicalRepository by koinInject()

    suspend operator fun invoke(
        id: String,
        moduleId: String
    ): Result<ChemicalSignoff> {
        return fromOfflineTaskFirst(
            offlineTaskId = id,
            remote = { repo.combineFetchAndTask(moduleId, id) }
        )
    }
}
