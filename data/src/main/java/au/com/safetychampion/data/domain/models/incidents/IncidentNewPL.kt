package au.com.safetychampion.data.domain.models.incidents

import au.com.safetychampion.data.data.api.IncidentAPI
import au.com.safetychampion.data.domain.Attachment
import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.core.Signature
import au.com.safetychampion.data.domain.models.* // ktlint-disable no-wildcard-imports
import au.com.safetychampion.data.domain.models.customvalues.CustomValue

/** Represents a [IncidentAPI.New] as the payload, old name is IncidentPojo*/
data class IncidentNewPL(
    override var attachments: MutableList<Attachment> = mutableListOf(),
    override var category: String? = null,
    override var categoryCusvals: MutableList<CustomValue>,
    override var categoryOther: String? = null,
    override var cusvals: MutableList<CustomValue> = mutableListOf(),
    override var dateOccurred: String? = null,
    override var description: String? = null,
    override var editComments: List<UpdateLog> = ArrayList(),
    override var geoData: GeoLatLng? = null,

    override var injuredBodyParts: List<InjuredBodyPart>? = null,
    override var injuredPersonLinks: List<InjuredPersonLink>? = null,
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
    var sessionId: String? = null,
    override var signatures: MutableList<Signature> = mutableListOf(),
    override var timeOccurred: TimeField? = null,
    override var tzDateCreated: String? = null,
    override var witnessName: String? = null,
    override var witnessPhone: String? = null
) : BasePL(), BaseIncident, IIncidentComponent

interface IIncidentComponent : ICusval, ICategoryCusval, IEnvCusval, ISignature, IAttachment
