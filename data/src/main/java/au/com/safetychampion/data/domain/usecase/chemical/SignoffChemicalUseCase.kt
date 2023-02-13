package au.com.safetychampion.data.domain.usecase.chemical

import au.com.safetychampion.data.data.chemical.IChemicalRepository
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.chemical.ChemicalSignoff
import au.com.safetychampion.data.domain.models.chemical.ChemicalTask
import au.com.safetychampion.data.domain.models.chemical.ChemicalTaskPL
import au.com.safetychampion.data.domain.usecase.BaseSignoffUseCase
import au.com.safetychampion.data.util.extension.koinInject

class SignoffChemicalUseCase : BaseSignoffUseCase<ChemicalTask, ChemicalSignoff>() {
    private val repo by koinInject<IChemicalRepository>()
    override suspend fun signoffOnline(payload: ChemicalSignoff): Result<ChemicalTask> {
        return repo.signoff(
            payload.body._id,
            payload.task._id!!,
            ChemicalTaskPL.fromModel(payload.task)
        )
    }
}
