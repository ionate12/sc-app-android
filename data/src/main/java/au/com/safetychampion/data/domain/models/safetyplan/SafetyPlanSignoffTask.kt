package au.com.safetychampion.data.domain.models.safetyplan

import androidx.databinding.ObservableField
import androidx.databinding.ObservableLong
import androidx.lifecycle.MutableLiveData
import au.com.safetychampion.data.domain.models.Tier
import au.com.safetychampion.data.domain.models.action.ActionLink
import au.com.safetychampion.data.domain.models.action.payload.Action
import au.com.safetychampion.data.domain.uncategory.Constants
import au.com.safetychampion.data.domain.uncategory.DocAttachment
import com.google.gson.annotations.SerializedName

data class SafetyPlanSignoffTask(
    var title: ObservableField<String> = ObservableField(),
    var description: ObservableField<String> = ObservableField(),
    var dateDue: ObservableField<String> = ObservableField(),
    var complete: Boolean = false,
    var completionNotes: ObservableField<String> = ObservableField(),
    var dateCompleted: ObservableField<String> = ObservableField(),
    var links: MutableLiveData<MutableList<ActionLink>> = MutableLiveData(mutableListOf()),
    var numTaskAssignees: ObservableLong = ObservableLong(),
    var attachments: MutableList<DocAttachment> = mutableListOf(),
    var newActions: MutableLiveData<MutableList<Action>> = MutableLiveData(mutableListOf()),
    var tzDateSignedoff: String = Constants.tz,
    var dateDueFrom: ObservableField<String> = ObservableField("DATE_DUE")
) {
    lateinit var type: String
    lateinit var _id: String

    @SerializedName("for")
    lateinit var _for: Tier
    lateinit var tier: Tier

    data class SafetyPlanSignoffPayload(
        var completionNotes: String? = null,
        var dateCompleted: String? = null,
        var tzDateSignedoff: String? = null,
        var dateDueFrom: String? = null,
        var attachments: List<DocAttachment>? = null,
        var links: List<ActionLink>? = null,
        var complete: Boolean = false
    )
}
