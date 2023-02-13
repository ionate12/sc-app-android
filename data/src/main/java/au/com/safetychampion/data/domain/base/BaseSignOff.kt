package au.com.safetychampion.data.domain.base

import au.com.safetychampion.data.domain.core.ModuleType

interface BaseSignOff<T : BaseTask> {
    val task: T
    fun syncableKey(): String
    fun type(): ModuleType
    companion object {
        fun actionSignoffSyncableKey(actionId: String) = "actions/$actionId/task/signoff"

        fun chemicalSignoffSyncableKey(
            chemicalId: String,
            taskId: String
        ) = "chemicals/$chemicalId/tasks/$taskId/signoff"

        fun criskSignoffSyncableKey(
            criskId: String,
            taskId: String
        ) = "crisks/$criskId/tasks/$taskId/signoff"
    }
}
