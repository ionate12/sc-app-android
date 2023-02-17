package au.com.safetychampion.data.domain.models.incidents

import au.com.safetychampion.data.domain.models.* // ktlint-disable no-wildcard-imports
import au.com.safetychampion.data.domain.models.customvalues.CustomValue

/** Sharing properties between [IncidentNewPL] and [Incident]*/
sealed interface BaseIncident : ICusval, ICategoryCusval, IEnvCusval, ISignature {
    var category: String?
    var categoryOther: String?
    override var categoryCusvals: MutableList<CustomValue>
    override var cusvals: MutableList<CustomValue>
    var dateOccurred: String?
    var description: String?
    var editComments: List<UpdateLog>
    var geoData: GeoLatLng?

    var injuredBodyParts: List<InjuredBodyPart>?
    var injuredPersonLinks: List<InjuredPersonLink>?
    var injuredPersonName: String?
    var injuredPersonPhone: String?
    var injuredPersonRole: String?
    var injuredPersonRoleOther: String?
    var injuryDescription: String?

    var location: String?
    var locationOther: String?

    var overview: String?
    var personReporting: String?
    var propOrEnvDamage: Boolean
    override var propOrEnvDamageCusvals: MutableList<CustomValue>
    var propOrEnvDamageDescription: String?

    var timeOccurred: TimeField?
    var tzDateCreated: String?
    var witnessName: String?
    var witnessPhone: String?
}
