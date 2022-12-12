package au.com.safetychampion.data.domain.models.chemical

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import au.com.safetychampion.data.domain.uncategory.Constants
import au.com.safetychampion.data.domain.uncategory.DocAttachment

data class ChemicalSignoffTask(
    var complete: ObservableBoolean = ObservableBoolean(false),
    var tzDateSignedoff: String? = Constants.tz,
    var completionNotes: ObservableField<String> = ObservableField<String>(),
    var dateCompleted: ObservableField<String> = ObservableField<String>(),
    var recurrent: ObservableBoolean = ObservableBoolean(true),
    var name: ObservableField<String> = ObservableField<String>(),
    var purpose: ObservableField<String> = ObservableField<String>(),
    var dateSdsIssued: ObservableField<String> = ObservableField<String>(),
    var dateToReview: ObservableField<String> = ObservableField<String>(),
    var attachments: MutableList<DocAttachment> = mutableListOf(),
    var hasSds: ObservableBoolean = ObservableBoolean(true)
)
