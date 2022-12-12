package au.com.safetychampion.data.domain.models.action

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import au.com.safetychampion.data.domain.models.customvalues.CustomValue
import au.com.safetychampion.data.domain.uncategory.ObservableNullableBoolean
import java.util.*

data class ActionSignOff(
    var body: ActionBody? = null,
    var title: String? = null,
    var dateCompleted: ObservableField<String> = ObservableField<String>(),
    var completionNotes: ObservableField<String> = ObservableField<String>(),
    var hazardCategory: ObservableField<String> = ObservableField<String>(),
    var hazardCategoryOther: ObservableField<String> = ObservableField<String>(),
    var complete: ObservableBoolean = ObservableBoolean(),
    var tzDateSignedoff: ObservableField<String> = ObservableField<String>(),
    var severity: ObservableField<String> = ObservableField<String>(),
    var controlLevel: ObservableField<String> = ObservableField<String>(),
    var controlLevelOther: ObservableField<String> = ObservableField<String>(),
    var cusvals: MutableLiveData<List<CustomValue>> = MutableLiveData(),
    var changeImplemented: ObservableNullableBoolean = ObservableNullableBoolean(),
    var newActions: MutableLiveData<List<ActionBody>> = MutableLiveData(),
    var links: MutableLiveData<List<ActionLink>> = MutableLiveData(),
    var taskId: String? = null,
    var sessionId: String? = null
)
