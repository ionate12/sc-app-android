package au.com.safetychampion.data.domain.usecase.crisk

import au.com.safetychampion.data.data.crisk.ICriskRepository
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.crisk.Crisk
import au.com.safetychampion.data.domain.usecase.BaseUseCase

class GetListCriskUseCase(
    private val repo: ICriskRepository
) : BaseUseCase() {
    suspend operator fun invoke(): Result<List<Crisk>> {
        return repo.list()
    }
}
