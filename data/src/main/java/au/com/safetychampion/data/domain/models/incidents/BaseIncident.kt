package au.com.safetychampion.data.domain.models.incidents

import au.com.safetychampion.data.domain.models.GeoLatLng
import au.com.safetychampion.data.domain.models.TimeField
import au.com.safetychampion.data.domain.models.UpdateLog
import au.com.safetychampion.data.domain.models.customvalues.CustomValue

/** Sharing properties between [IncidentNewPL] and [Incident]*/
sealed interface BaseIncident {
    var category: String?
    var categoryOther: String?
    var categoryCusvals: MutableList<CustomValue>
    var cusvals: MutableList<CustomValue>
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
    var propOrEnvDamageCusvals: MutableList<CustomValue>
    var propOrEnvDamageDescription: String?

    var timeOccurred: TimeField?
    var tzDateCreated: String?
    var witnessName: String?
    var witnessPhone: String?
}
