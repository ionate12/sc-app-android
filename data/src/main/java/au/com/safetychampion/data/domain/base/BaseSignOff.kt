package au.com.safetychampion.data.domain.base

import au.com.safetychampion.data.domain.core.ModuleType

interface BaseSignOff<T : BaseTask> {
    val task: T
    fun syncableKey(): String
    fun type(): ModuleType
}
