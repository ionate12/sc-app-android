package au.com.safetychampion.data.domain.usecase.inspection

import au.com.safetychampion.data.data.inspection.IInspectionRepository
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.inspections.Inspection
import au.com.safetychampion.data.domain.usecase.BaseUseCase
import au.com.safetychampion.data.util.extension.koinInject

class GetAvailableListInspectionUseCase : BaseUseCase() {
    private val repo: IInspectionRepository by koinInject()
    suspend operator fun invoke(): Result<List<Inspection>> = repo.getAvailableList()
}
