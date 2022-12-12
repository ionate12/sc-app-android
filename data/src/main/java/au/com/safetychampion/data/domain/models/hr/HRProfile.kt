package au.com.safetychampion.data.domain.models.hr

import androidx.databinding.BaseObservable
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import au.com.safetychampion.data.domain.models.CreatedBy
import au.com.safetychampion.data.domain.models.Tier
import au.com.safetychampion.data.domain.models.incidents.LinkedIncident
import au.com.safetychampion.data.domain.uncategory.DocAttachment

data class HRProfile(
    var _id: String? = null,
    var type: String? = null,
    var tier: Tier? = null,
    var name: ObservableField<String> = ObservableField(),
    var email: ObservableField<String> = ObservableField(),
    var phone: ObservableField<String> = ObservableField(),
    var personalEmail: ObservableField<String> = ObservableField(),
    var personalPhone: ObservableField<String> = ObservableField(),
    var postalAddress: ObservableField<String> = ObservableField(),
    var emergencyContactName: ObservableField<String> = ObservableField(),
    var emergencyContactPhone: ObservableField<String> = ObservableField(),
    var referenceId: ObservableField<String> = ObservableField(),
    var position: ObservableField<String> = ObservableField(),
    var managerName: ObservableField<String> = ObservableField(),
    var managerEmail: ObservableField<String> = ObservableField(),
    var notes: ObservableField<String> = ObservableField(),
    var attachments: MutableList<DocAttachment> = mutableListOf(),
    var gender: ObservableField<String> = ObservableField(),
    var genderOther: ObservableField<String> = ObservableField(),
    var dateOfBirth: ObservableField<String> = ObservableField(),
    var dateStarted: ObservableField<String> = ObservableField(),
    var createdBy: CreatedBy? = null,
    var tzDateCreated: ObservableField<String> = ObservableField(),
    var dateCreated: ObservableField<String> = ObservableField(),
    var linkedIncidents: MutableLiveData<List<LinkedIncident>> = MutableLiveData(listOf())
) : BaseObservable()
