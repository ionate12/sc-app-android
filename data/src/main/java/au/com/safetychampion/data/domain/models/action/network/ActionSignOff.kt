package au.com.safetychampion.data.domain.models.action.network

import au.com.safetychampion.data.domain.base.BaseSignOff
import au.com.safetychampion.data.domain.core.ModuleType
import au.com.safetychampion.data.domain.models.action.Action
import au.com.safetychampion.data.domain.models.action.ActionTask

data class ActionSignOff(
    var body: Action,
    override val task: ActionTask
) : BaseSignOff<ActionTask> {
    override fun syncableKey(): String = BaseSignOff.actionSignoffSyncableKey(body._id)
    override fun type(): ModuleType = ModuleType.ACTION
}
