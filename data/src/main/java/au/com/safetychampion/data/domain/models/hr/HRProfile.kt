package au.com.safetychampion.data.domain.models.hr

import androidx.lifecycle.MutableLiveData
import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.models.Tier
import au.com.safetychampion.data.domain.models.incidents.LinkedIncident
import au.com.safetychampion.data.domain.models.workplace.CreatedBy
import au.com.safetychampion.data.domain.uncategory.DocAttachment

data class HRProfile(
    var _id: String? = null,
    var type: String? = null,
    var tier: Tier? = null,
    var name: String? = null,
    var email: String? = null,
    var phone: String? = null,
    var personalEmail: String? = null,
    var personalPhone: String? = null,
    var postalAddress: String? = null,
    var emergencyContactName: String? = null,
    var emergencyContactPhone: String? = null,
    var referenceId: String? = null,
    var position: String? = null,
    var managerName: String? = null,
    var managerEmail: String? = null,
    var notes: String? = null,
    var attachments: MutableList<DocAttachment> = mutableListOf(),
    var gender: String? = null,
    var genderOther: String? = null,
    var dateOfBirth: String? = null,
    var dateStarted: String? = null,
    var createdBy: CreatedBy? = null,
    var tzDateCreated: String? = null,
    var dateCreated: String? = null,
    var linkedIncidents: MutableLiveData<List<LinkedIncident>> = MutableLiveData(listOf())
) : BasePL()
