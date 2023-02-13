package au.com.safetychampion.data.domain.models.crisk

import au.com.safetychampion.data.domain.base.BaseSignOff
import au.com.safetychampion.data.domain.core.ModuleType

data class CriskSignoff(
    val body: Crisk, // TODO("this is notnull, fix CustomValueDropdown bug in cusval first ")
    override val task: CriskTask
) : BaseSignOff<CriskTask> {
    override fun syncableKey(): String = "crisks/${body._id}/tasks/${task._id}/signoff"

    override fun type(): ModuleType = ModuleType.CRISK
}
