package au.com.safetychampion.data.domain.models.chemical

import au.com.safetychampion.data.domain.models.Tier
import au.com.safetychampion.data.domain.uncategory.DocAttachment
import com.google.gson.JsonObject

data class Chemical(
    var _id: String? = null,
    var allocationActive: Boolean = false,
    var allocationOf: JsonObject? = null,
    var attachments: List<DocAttachment>? = null,
    var createdBy: JsonObject? = null,
    var dateCreated: String? = null,
    var emergencyContactPhone: String? = null,
    var locations: List<ChemicalLocation>? = null,
    var name: String? = null,
    var purpose: String? = null,
    var risk: ChemicalRisk? = null,
    var sds: ChemicalSDS? = null,
    var shippingName: String? = null,
    var supplierName: String? = null,
    var tier: Tier? = null,
    var transport: ChemicalTransport? = null,
    var type: String? = null,
    var tzDateCreated: String? = null
)
