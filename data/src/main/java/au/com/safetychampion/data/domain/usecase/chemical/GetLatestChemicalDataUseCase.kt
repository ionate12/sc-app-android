package au.com.safetychampion.data.domain.usecase.chemical

import au.com.safetychampion.data.data.chemical.IChemicalRepository
import au.com.safetychampion.data.domain.models.GHSCode
import au.com.safetychampion.data.domain.models.chemical.Chemical
import au.com.safetychampion.data.domain.usecase.BaseUseCase
import kotlinx.coroutines.flow.Flow

class GetLatestChemicalDataUseCase(
    private val repo: IChemicalRepository
) : BaseUseCase() {

    operator fun invoke(): Flow<Pair<List<Chemical>, GHSCode>> {
        return repo.latestChemicalData
    }
}
