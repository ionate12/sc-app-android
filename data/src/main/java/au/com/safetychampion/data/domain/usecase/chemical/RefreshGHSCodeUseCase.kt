package au.com.safetychampion.data.domain.usecase.chemical

import au.com.safetychampion.data.data.chemical.IChemicalRepository
import au.com.safetychampion.data.domain.usecase.BaseUseCase
import au.com.safetychampion.util.koinInject

class RefreshGHSCodeUseCase : BaseUseCase() {

    private val repo: IChemicalRepository by koinInject()

    suspend operator fun invoke() = repo.refreshGHSCodeList()
}
