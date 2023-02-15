package au.com.safetychampion.data.domain.usecase.crisk // ktlint-disable filename

import au.com.safetychampion.data.data.crisk.ICriskRepository
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.crisk.CriskSignoff
import au.com.safetychampion.data.domain.models.crisk.CriskTask
import au.com.safetychampion.data.domain.models.crisk.CriskTaskPL
import au.com.safetychampion.data.domain.usecase.BaseSignoffUseCase
import au.com.safetychampion.data.util.extension.koinInject

class SignoffCriskUseCase : BaseSignoffUseCase<CriskTask, CriskSignoff>() {
    private val repo by koinInject<ICriskRepository>()
    override suspend fun signoffOnline(payload: CriskSignoff): Result<CriskTask> {
        return repo.signoff(payload.body._id, payload.task._id, CriskTaskPL.fromModel(payload.task))
    }
}
