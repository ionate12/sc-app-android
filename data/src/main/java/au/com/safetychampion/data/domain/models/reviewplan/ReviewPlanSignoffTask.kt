package au.com.safetychampion.data.domain.models.reviewplan

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import au.com.safetychampion.data.domain.core.Signature
import au.com.safetychampion.data.domain.models.action.ActionLink
import au.com.safetychampion.data.domain.models.action.network.ActionPL
import au.com.safetychampion.data.domain.models.customvalues.CustomValue
import au.com.safetychampion.data.domain.uncategory.Constants
import au.com.safetychampion.data.domain.uncategory.DocAttachment
import com.google.gson.annotations.SerializedName

open class ReviewPlanSignoffTask(
    @SerializedName("for")
    var _for: ReviewPlanReview? = null,
    var dateDue: String? = "",
    var save: Boolean = false,
    var description: String = "",
    var tzDateSignedoff: String = Constants.tz,
    var complete: ObservableBoolean = ObservableBoolean(true),
    var dateCompleted: ObservableField<String> = ObservableField(),
    var reviewNotes: ObservableField<String> = ObservableField(),
    var recurrent: ObservableBoolean = ObservableBoolean(true),
    var referenceId: ObservableField<String> = ObservableField(),
    var notes: ObservableField<String> = ObservableField(),
    var dateExpiry: ObservableField<String> = ObservableField(),
    var dateIssued: ObservableField<String> = object : ObservableField<String>() {
        override fun set(value: String?) {
            super.set(value)
            if (!dateExpiry.get().isNullOrEmpty() && !value.isNullOrEmpty() && dateExpiry.get()!! < value) {
                dateExpiry.set(value)
            }
            if (!dateCompleted.get().isNullOrEmpty() && !value.isNullOrEmpty() && dateCompleted.get()!! < value) {
                dateCompleted.set(value)
            }
        }
    },
    var cusvals: MutableLiveData<MutableList<CustomValue>> = MutableLiveData(mutableListOf()),
    var subcategoryCusvals: MutableLiveData<MutableList<CustomValue>> = MutableLiveData(mutableListOf()),
    var attachments: MutableList<DocAttachment> = mutableListOf(),
    var signatures: MutableList<Signature> ? = mutableListOf(),
    var actionLinks: MutableList<ActionLink> = mutableListOf(),
    var newActions: MutableList<ActionPL> = mutableListOf()
) {

    private fun ObservableField<String>.setValueWithNullCheck(value: String?) {
        if (value == null || value.isEmpty()) {
            return
        }
        this.set(value)
    }

    fun ObservableField<String>.setValue(value: String?): ObservableField<String> {
        if (this == null) return ObservableField(value)
        this.setValueWithNullCheck(value)
        return this
    }

    class PayloadNoLonger {
        var complete = true
        var dateCompleted: String? = null
        var reviewNotes: String? = null
        var recurrent = false
        var tzDateSignedoff: String = Constants.tz
        var signatures: MutableList<Signature> ? = null
    }
}
