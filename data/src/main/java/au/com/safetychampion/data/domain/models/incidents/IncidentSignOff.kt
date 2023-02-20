package au.com.safetychampion.data.domain.models.incidents

import au.com.safetychampion.data.domain.base.BaseSignOff
import au.com.safetychampion.data.domain.core.ModuleType

data class IncidentSignOff(
    var body: Incident = Incident(),
    override var task: IncidentTask = IncidentTask()
) : BaseSignOff<IncidentTask> {
    override fun syncableKey() = BaseSignOff.incidentSignoffSyncableKey(body._id!!)

    override fun type(): ModuleType = ModuleType.INCIDENT
}
