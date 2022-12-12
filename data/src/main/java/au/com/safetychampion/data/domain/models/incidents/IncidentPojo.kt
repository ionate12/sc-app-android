package au.com.safetychampion.data.domain.models.incidents

import au.com.safetychampion.data.domain.models.TimeField
import au.com.safetychampion.data.domain.models.UpdateLog
import au.com.safetychampion.data.domain.models.customvalues.CusvalPojo
import com.google.gson.JsonArray
import java.util.*

data class IncidentPojo(
    var sessionId: String? = null,
    var attachments: JsonArray? = null,
    var tzDateCreated: String? = null,
    var dateOccurred: String? = null,
    var timeOccurred: TimeField? = null,
    var overview: String? = null,
    var description: String? = null,

    // Category getData from configuration.
    var category: String? = null,
    var categoryOther: String? = null,

    // Location getData from configuration.
    var location: String? = null,
    var locationOther: String? = null,
//    var geoData: GeoLatLng? = null,
    var personReporting: String? = null,
    var witnessPhone: String? = null,
    var witnessName: String? = null,

    // Injury
    var injuredPersonName: String? = null,
    var injuredPersonPhone: String? = null,
    var injuredPersonRole: String? = null,
    var injuredPersonRoleOther: String? = null,
    var injuryDescription: String? = null,
    var injuredPersonLinks: List<InjuredPersonLink>? = null,
    var propOrEnvDamage: Boolean = false,
    var propOrEnvDamageDescription: String? = null,
    var editComments: List<UpdateLog> = ArrayList(),
    var injuredBodyParts: List<InjuredBodyPartPojo>? = null,
    var cusvals: List<CusvalPojo?>? = null,
    var propOrEnvDamageCusvals: List<CusvalPojo?>? = null,
    var categoryCusvals: List<CusvalPojo?>? = null,
    var signatures: List<SignaturePayloadIncident>? = null
)
