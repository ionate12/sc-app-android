package au.com.safetychampion.data.domain.models.incidents

import androidx.databinding.BaseObservable
import au.com.safetychampion.data.domain.models.CreatedBy
import au.com.safetychampion.data.domain.models.GeoLatLng
import au.com.safetychampion.data.domain.models.Tier
import au.com.safetychampion.data.domain.models.TimeField
import au.com.safetychampion.data.domain.models.UpdateLog
import au.com.safetychampion.data.domain.models.customvalues.CusvalPojo
import au.com.safetychampion.data.domain.uncategory.DocAttachment
import java.util.ArrayList

data class IncidentReviewPojo(
    var _id: String? = null,
    var taskId: String? = null,
    var type: String? = null,
    var tier: Tier? = null,
    var overview: String? = null,
    var description: String? = null,
    var timeOccurred: TimeField? = null,
    var personReporting: String? = null,
    var witnessName: String? = null,
    var witnessPhone: String? = null,
    var injuredPersonName: String? = null,
    var injuredPersonPhone: String? = null,
    var injuredPersonLinks: List<InjuredPersonLink> = ArrayList(),
    var injuryDescription: String? = null,
    var injuredBodyParts: ArrayList<InjuredBodyPartPojo> = ArrayList<InjuredBodyPartPojo>(),
    var propOrEnvDamage: Boolean = false,
    var propOrEnvDamageDescription: String? = null,
    var attachments: List<DocAttachment> = ArrayList(),
    var cusvals: ArrayList<CusvalPojo> = ArrayList<CusvalPojo>(),
    var propOrEnvDamageCusvals: ArrayList<CusvalPojo> = ArrayList<CusvalPojo>(),
    var categoryCusvals: ArrayList<CusvalPojo> = ArrayList<CusvalPojo>(),
    var editComments: ArrayList<UpdateLog> = ArrayList<UpdateLog>(),
    var location: String? = null,
    var locationOther: String? = null,
    var geoData: GeoLatLng? = null,
    var category: String? = null,
    var categoryOther: String? = null,
    var injuredPersonRole: String? = null,
    var injuredPersonRoleOther: String? = null,
    var dateOccurred: String? = null,
    var createdBy: CreatedBy? = null,
    var tzDateCreated: String? = null,
    var dateCreated: String? = null,
    var dateDue: String? = null,
    var reference: String? = null,
    var closed: Boolean = false,
    var signatures: List<SignaturePayloadIncident>? = null,

    // added in sprint 3.4
    var archived: Boolean = false
) : BaseObservable()
