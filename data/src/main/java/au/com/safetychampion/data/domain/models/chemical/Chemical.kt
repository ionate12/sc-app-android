package au.com.safetychampion.data.domain.models.chemical

import au.com.safetychampion.data.domain.base.BaseModule
import au.com.safetychampion.data.domain.base.BaseModuleImpl
import au.com.safetychampion.data.domain.models.Tier
import au.com.safetychampion.data.domain.models.workplace.CreatedBy
import au.com.safetychampion.data.domain.uncategory.DocAttachment

data class Chemical(
    val allocationOf: BaseModuleImpl? = null,
    val attachments: List<DocAttachment>? = null,
    val allocationActive: Boolean = false,
    val createdBy: CreatedBy? = null,
    val dateCreated: String? = null,
    val emergencyContactPhone: String? = null,
    val locations: List<ChemicalLocation>? = null,
    val name: String? = null,
    val purpose: String? = null,
    val risk: ChemicalRisk? = null,
    val sds: ChemicalSDS? = null,
    val shippingName: String? = null,
    val supplierName: String? = null,
    val tier: Tier? = null,
    val transport: ChemicalTransport? = null,
    val tzDateCreated: String? = null,
    override val _id: String,
    override val type: String
) : BaseModule {

    fun setWPName(wpName: String) {
        if (locations.isNullOrEmpty().not()) {
            locations!!.forEach { it.wpName = wpName }
        }
    }
}
