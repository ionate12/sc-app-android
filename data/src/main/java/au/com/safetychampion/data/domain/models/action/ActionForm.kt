package au.com.safetychampion.data.domain.models.action

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import au.com.safetychampion.data.domain.models.CreatedBy
import au.com.safetychampion.data.domain.models.ForTask
import au.com.safetychampion.data.domain.models.Tier
import au.com.safetychampion.data.domain.models.UpdateLog
import au.com.safetychampion.data.domain.models.customvalues.CustomValue
import au.com.safetychampion.data.domain.uncategory.DocAttachment
import java.util.*

class ActionForm(
    var description: ObservableField<String> = ObservableField(),
    var category: ObservableField<String> = ObservableField(),
    var categoryOther: ObservableField<String> = ObservableField(),
    var personReporting: ObservableField<String> = ObservableField(),
    var personResponsible: ObservableField<String> = ObservableField(),
    var personResponsibleEmail: ObservableField<String> = ObservableField(),
    private var _dateIdentified: ObservableField<String> = ObservableField<String>(),
    var dateDue: ObservableField<String> = ObservableField(),
    var tzDateCreated: ObservableField<String> = ObservableField(),
    var dateCreated: ObservableField<String> = ObservableField(),
    var createdBy: CreatedBy? = null,
    var sessionId: ObservableField<String> = ObservableField(),
    var reference: String? = null,
    var editComments: List<UpdateLog> = ArrayList(),
    var comment: ObservableField<String> = ObservableField(),
    var attachments: MutableList<DocAttachment> = mutableListOf(),
    var closed: Boolean = false,

    // Added in Sprint 3.4
    var archived: Boolean = false,

    // Update Cusvals
    var cusvals: MutableLiveData<MutableList<CustomValue>> = MutableLiveData(mutableListOf()),
    var categoryCusvals: MutableLiveData<MutableList<CustomValue>> = MutableLiveData(mutableListOf()),

    // Extra Fields for Signoff
    var _temp: ForTask? = null,
    var minDateIdentified: String? = null,
    var _id: String? = null,
    var taskId: String? = null,
    var type: String? = null,
    var tier: Tier? = null,
    var userId: String? = null,
    var overview: ObservableField<String> = ObservableField()
) {
    var dateIdentified = object : ObservableField<String>() {
        override fun set(value: String?) {
            _dateIdentified.set(value)
            if (dateDue.get() != null) {
                if (dateDue.get()!! < value!!) {
                    dateDue.set(value)
                }
            }
        }

        override fun get(): String? = _dateIdentified.get()
    }
}
