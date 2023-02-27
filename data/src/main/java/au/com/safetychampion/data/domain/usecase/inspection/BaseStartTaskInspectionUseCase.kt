package au.com.safetychampion.data.domain.usecase.inspection

import au.com.safetychampion.data.data.inspection.IInspectionRepository
import au.com.safetychampion.data.domain.base.BaseModuleImpl
import au.com.safetychampion.data.domain.core.ModuleType
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.core.map
import au.com.safetychampion.data.domain.models.inspections.Inspection
import au.com.safetychampion.data.domain.models.inspections.InspectionTask
import au.com.safetychampion.data.domain.uncategory.Constants
import au.com.safetychampion.data.domain.usecase.BaseUseCase
import au.com.safetychampion.data.util.extension.SCDateFormat
import au.com.safetychampion.data.util.extension.koinInject
import au.com.safetychampion.data.util.extension.toReadableString
import java.util.Date

abstract class BaseStartTaskInspectionUseCase : BaseUseCase() {
    protected val repo: IInspectionRepository by koinInject()

    protected suspend fun simulateTaskInternal(
        inspId: String
    ): Result<InspectionTask> {
        // fetch Inspection Offline then simulate child
        return repo.fetch(inspId).map {
            val _for = BaseModuleImpl(it._id, it.type.value)
            val task = InspectionTask(
                _for = _for,
                title = "Inspection: ${it.title}",
                type = ModuleType.TASK.value,
                tzDateStarted = Constants.tz,
                inExecution = true,
                snapshot = mappingToSnapshot(it),
                description = "",
                dateStarted = Date().toReadableString(SCDateFormat.YYYY_MM_DD)
            )
            task
        }
    }

    private fun mappingToSnapshot(inspection: Inspection): InspectionTask.Snapshot = with(inspection) {
        InspectionTask.Snapshot(
            title = this.title,
            description = this.description,
            category = this.category,
            categoryOther = this.categoryOther,
            subcategory = this.subcategory,
            subcategoryOther = this.subcategoryOther,
            frequencyKey = this.frequencyKey,
            frequencyValue = this.frequencyValue,
            template = this.template ?: InspectionTask.Template(),
            templateMeta = this.templateMeta,
            emailList = emailList
        )
    }
}
