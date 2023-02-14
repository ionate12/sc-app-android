package au.com.safetychampion.data.domain.models.chemical

import au.com.safetychampion.data.domain.base.BaseSignOff
import au.com.safetychampion.data.domain.core.ModuleType

data class ChemicalSignoff(
    val body: Chemical,
    override var task: ChemicalTask
) : BaseSignOff<ChemicalTask> {
    override fun syncableKey(): String = BaseSignOff.chemicalSignoffSyncableKey(body._id, task._id)

    override fun type(): ModuleType = ModuleType.CHEMICAL
}
