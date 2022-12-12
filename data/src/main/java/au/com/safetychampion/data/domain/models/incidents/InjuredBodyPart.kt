package au.com.safetychampion.data.domain.models.incidents

import androidx.databinding.BaseObservable
import androidx.databinding.ObservableField
import java.util.*

data class InjuredBodyPart(
    var _id: ObservableField<String?>,
    var bodyPart: ObservableField<String?>,
    var bodyPartOther: ObservableField<String?>,
    var injury: ObservableField<String?>,
    var injuryOther: ObservableField<String?>,
    var comment: ObservableField<String?>
) : BaseObservable() {

    init {
        _id = ObservableField(UUID.randomUUID().toString())
        bodyPart = object : ObservableField<String?>() {
            override fun set(value: String?) {
                if (value?.lowercase(Locale.getDefault()) == "other") {
                    injury.set("Other")
                }
                super.set(value)
            }
        }
        bodyPartOther = ObservableField()
        injury = ObservableField()
        injuryOther = ObservableField()
        comment = ObservableField()
    }
}
