package au.com.safetychampion.data.domain.models.incidents

import au.com.safetychampion.data.domain.base.BaseSignOff
import au.com.safetychampion.data.domain.core.ModuleType

data class IncidentSignOff(
    var body: Incident = Incident(),
    override var task: IncidentTask = IncidentTask()
) : BaseSignOff<IncidentTask> {
    override fun syncableKey() = "incidents/${task._id}/task/signoff"

    override fun type(): ModuleType = ModuleType.DOCUMENT
}
