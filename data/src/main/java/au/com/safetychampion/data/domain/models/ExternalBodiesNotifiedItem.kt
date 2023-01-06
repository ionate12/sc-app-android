package au.com.safetychampion.data.domain.models

import androidx.databinding.BaseObservable
import androidx.databinding.ObservableField

class ExternalBodiesNotifiedItem(
    var date: ObservableField<String> = ObservableField(),
    var name: ObservableField<String> = ObservableField(),
    var comment: ObservableField<String> = ObservableField(),
    var nameOther: ObservableField<String> = ObservableField()
) : BaseObservable()
