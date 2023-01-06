package au.com.safetychampion.data.domain.models.incidents

import androidx.databinding.BaseObservable
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData

data class IncidentFormUiData(
    val expandInjurySection: ObservableBoolean = ObservableBoolean(false),
    val submissionMsg: MutableLiveData<String> = MutableLiveData(),
    var autoSuggestPersonRoleError: ObservableBoolean = ObservableBoolean(false)
) : BaseObservable()
