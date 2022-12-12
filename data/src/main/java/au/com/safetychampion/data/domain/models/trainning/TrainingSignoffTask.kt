package au.com.safetychampion.data.domain.models.trainning

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import au.com.safetychampion.data.domain.uncategory.Constants
import au.com.safetychampion.data.domain.uncategory.DocAttachment

class TrainingSignoffTask {
    var complete: Boolean = true
    var dateCompleted: ObservableField<String> = object : ObservableField<String>() {
        override fun set(value: String?) {
            super.set(value)
            if (!dateExpiry.get().isNullOrEmpty() && !value.isNullOrEmpty() && dateExpiry.get()!! < value) {
                dateExpiry.set(value)
            }
        }
    }
    var completionNotes: ObservableField<String> = ObservableField()
    var recurrent: ObservableBoolean = ObservableBoolean(true)
    var dateExpiry: ObservableField<String> = ObservableField()
    var name: ObservableField<String> = ObservableField()
    var nameOther: ObservableField<String> = ObservableField()
    var referenceId: ObservableField<String> = ObservableField()
    var attachments: MutableList<DocAttachment> = mutableListOf()
    var tzDateSignedoff = Constants.tz

   /* var type: ObservableField<String> = ObservableField()
    var id: ObservableField<String> = ObservableField()
    var tier: Tier? = null
    var owner: Tier? = null
    var dateIssued: ObservableField<String> = ObservableField()
    var createdBy: CreatedBy? = null
    var tzDateCreated: ObservableField<String> = ObservableField()
    var dateCreated: ObservableField<String> = ObservableField()*/
}
