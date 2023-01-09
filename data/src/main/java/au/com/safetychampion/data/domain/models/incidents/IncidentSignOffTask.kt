package au.com.safetychampion.data.domain.models.incidents

import androidx.databinding.BaseObservable
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import au.com.safetychampion.data.domain.models.ExternalBodyPojo
import au.com.safetychampion.data.domain.models.action.ActionLink
import au.com.safetychampion.data.domain.models.action.ActionPojo
import au.com.safetychampion.data.domain.models.customvalues.CustomValue
import au.com.safetychampion.data.domain.uncategory.DocAttachment
import au.com.safetychampion.data.domain.uncategory.ObservableNullableBoolean

data class IncidentSignOffTask(
    var taskId: String? = null, // replacing _id
    var sessionId: String? = null,
    var title: String? = null,
    var attachments: MutableList<DocAttachment> = mutableListOf(),
    var severity: ObservableField<String> = ObservableField(),
    var changeNotes: ObservableField<String> = ObservableField(),
    var changeImplemented: ObservableNullableBoolean = ObservableNullableBoolean(),
    var controlReviewedOther: ObservableField<String> = ObservableField(),
    var cusvals: MutableLiveData<MutableList<CustomValue>> = MutableLiveData(mutableListOf()),
    var tzDateSignedoff: ObservableField<String> = ObservableField(),
    var lostTimeInjury: ObservableField<String> = ObservableField(),
    var hazardCategory: ObservableField<String> = ObservableField(),
    var hazardCategoryOther: ObservableField<String> = ObservableField(),
    var externalBodiesNotified: MutableLiveData<MutableList<ExternalBodyPojo>> = MutableLiveData(mutableListOf()), // set emty before supmit
    var controlReviewed: ObservableField<String> = ObservableField(),
    var controlLevelOther: ObservableField<String> = ObservableField(),
    var dateCompleted: ObservableField<String> = ObservableField(),
    var controlLevel: ObservableField<String> = ObservableField(),
    var links: MutableLiveData<MutableList<ActionLink>> = MutableLiveData(mutableListOf()),
    var hasExternalBody: ObservableNullableBoolean = ObservableNullableBoolean(),
    var complete: Boolean? = null,
    var newActions: MutableLiveData<MutableList<ActionPojo>> = MutableLiveData(mutableListOf())
) : BaseObservable()
