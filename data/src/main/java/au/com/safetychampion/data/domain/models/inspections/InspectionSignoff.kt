package au.com.safetychampion.data.domain.models.inspections

import au.com.safetychampion.data.domain.base.BaseSignOff
import au.com.safetychampion.data.domain.core.ModuleType

class InspectionSignoff(
    val inspectionId: String,
    override val task: InspectionTask
) : BaseSignOff<InspectionTask> {
    override fun syncableKey(): String = BaseSignOff.inspectionSignoffSyncableKey(inspectionId, task._id!!)

    override fun type(): ModuleType = ModuleType.INSPECTION
}
