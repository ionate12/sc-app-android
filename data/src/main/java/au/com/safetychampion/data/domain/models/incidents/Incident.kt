package au.com.safetychampion.data.domain.models.incidents

import au.com.safetychampion.data.data.api.IncidentAPI
import au.com.safetychampion.data.domain.core.Signature
import au.com.safetychampion.data.domain.models.* // ktlint-disable no-wildcard-imports
import au.com.safetychampion.data.domain.models.customvalues.CustomValue
import au.com.safetychampion.data.domain.uncategory.DocAttachment

/** Represents a fully Incident, or the return model for [IncidentAPI.New], [IncidentAPI.List] and [IncidentAPI.Fetch]*/
data class Incident(
    var _id: String? = null,
    var taskId: String? = null,

    var archived: Boolean = false,
    var attachments: List<DocAttachment>? = emptyList(),
    override var category: String? = null,
    override var categoryCusvals: MutableList<CustomValue> = mutableListOf(),
    override var categoryOther: String? = null,
    var closed: Boolean = false,
    var createdBy: CreatedBy? = null,
    override var cusvals: MutableList<CustomValue> = mutableListOf(),
    var dateCreated: String? = null,
    var dateDue: String? = null,
    override var dateOccurred: String? = null,
    override var description: String? = null,
    override var editComments: List<UpdateLog> = emptyList(),
    override var geoData: GeoLatLng? = null,

    override var injuredBodyParts: List<InjuredBodyPart>? = emptyList(),
    override var injuredPersonLinks: List<InjuredPersonLink>? = emptyList(),
    override var injuredPersonName: String? = null,
    override var injuredPersonPhone: String? = null,
    override var injuredPersonRole: String? = null,
    override var injuredPersonRoleOther: String? = null,
    override var injuryDescription: String? = null,

    override var location: String? = null,
    override var locationOther: String? = null,

    override var overview: String? = null,
    override var personReporting: String? = null,
    override var propOrEnvDamage: Boolean = false,
    override var propOrEnvDamageCusvals: MutableList<CustomValue> = mutableListOf(),
    override var propOrEnvDamageDescription: String? = null,
    var reference: String? = null,
    override var signatures: MutableList<Signature> = mutableListOf(),
    var tier: Tier? = null,
    override var timeOccurred: TimeField? = null,
    var type: String? = null,
    override var tzDateCreated: String? = null,
    override var witnessName: String? = null,
    override var witnessPhone: String? = null
) : BaseIncident
