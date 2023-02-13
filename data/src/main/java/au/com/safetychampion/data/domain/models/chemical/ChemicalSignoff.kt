package au.com.safetychampion.data.domain.models.chemical

import au.com.safetychampion.data.domain.base.BaseSignOff
import au.com.safetychampion.data.domain.core.ModuleType

data class ChemicalSignoff(
    val body: Chemical,
    override var task: ChemicalTask
) : BaseSignOff<ChemicalTask> {
    override fun syncableKey(): String = "chemicals/${body._id}/tasks/${task._id}/signoff"

    override fun type(): ModuleType = ModuleType.CHEMICAL
}
