package au.com.safetychampion.data.domain.models.crisk

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import au.com.safetychampion.data.domain.InspectionFormPayload
import au.com.safetychampion.data.domain.models.SCHolderLink
import au.com.safetychampion.data.domain.models.action.ActionLink
import au.com.safetychampion.data.domain.models.action.ActionPojo
import au.com.safetychampion.data.domain.models.customvalues.CustomValue
import au.com.safetychampion.data.domain.uncategory.Constants
import au.com.safetychampion.data.domain.uncategory.DocAttachment
import com.google.gson.annotations.SerializedName

data class CriskTask(
    var _id: String = "",
    @SerializedName("for")
    var _for: Crisk? = null,
    var dateDue: String? = null,
    var save: Boolean = false,
    var description: String = "",
    var tzDateSignedoff: String = Constants.tz,
    var complete: ObservableBoolean = ObservableBoolean(true),
    var dateCompleted: ObservableField<String> = ObservableField(),
    var title: ObservableField<String> = ObservableField(""),
    var reviewNotes: ObservableField<String> = ObservableField(),
    var recurrent: ObservableBoolean = ObservableBoolean(true),
    var referenceId: ObservableField<String> = ObservableField(),
    var notes: ObservableField<String> = ObservableField(),
    var archiveNotes: ObservableField<String> = ObservableField(),
    var inherentRiskRating: ObservableField<String> = ObservableField(),
    var inherentRiskRatingOther: ObservableField<String?> = ObservableField(),
    var currentMitigation: ObservableField<String> = ObservableField(""),
    var residualRisk: ObservableField<String> = ObservableField(""),
    var residualRiskOther: ObservableField<String?> = ObservableField(),
    var futureControl: ObservableBoolean = ObservableBoolean(),
    var futureMitigation: ObservableField<String?> = ObservableField(),
    var futureRiskRating: ObservableField<String?> = ObservableField(),
    var futureRiskRatingOther: ObservableField<String?> = ObservableField(),
    var riskOwner: MutableLiveData<String> = MutableLiveData(),
    var riskOwnerOther: ObservableField<String?> = ObservableField(),
    var dateIssued: ObservableField<String> = ObservableField(),
    var dateExpiry: ObservableField<String> = ObservableField(),
    var cusvals: MutableLiveData<MutableList<CustomValue>> = MutableLiveData(mutableListOf()),
    var subcategoryCusvals: MutableLiveData<MutableList<CustomValue>> = MutableLiveData(
        mutableListOf()
    ),
    var riskOwnerLinks: MutableLiveData<MutableList<SCHolderLink>> = MutableLiveData(mutableListOf()),
    var attachments: MutableList<DocAttachment> = mutableListOf(),
    var signatures: MutableList<InspectionFormPayload.SignaturePayload>? = mutableListOf(),
    var actionLinks: MutableList<ActionLink> = mutableListOf(),
    var newActions: MutableList<ActionPojo> = mutableListOf(),

    @Transient
    var autoSuggestShowing: ObservableBoolean = ObservableBoolean(false)
)
