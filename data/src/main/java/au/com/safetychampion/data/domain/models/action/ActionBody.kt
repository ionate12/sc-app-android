package au.com.safetychampion.data.domain.models.action

import android.net.Uri
import androidx.core.util.Consumer
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import au.com.safetychampion.data.domain.models.CreatedBy
import au.com.safetychampion.data.domain.models.ForTask
import au.com.safetychampion.data.domain.models.Tier
import au.com.safetychampion.data.domain.models.UpdateLog
import au.com.safetychampion.data.domain.models.customvalues.CustomValue
import au.com.safetychampion.data.domain.uncategory.Constants
import com.google.gson.JsonArray
import com.google.gson.annotations.Expose

data class ActionBody(
    val _id: String? = null,
    val tier: Tier? = null,
    val userId: String? = null,
    val overview: String? = null,
    val description: String? = null,
    val category: String? = null,
    val categoryOther: String? = null,
    val personReporting: String? = null,
    val personResponsible: String? = null,
    val personResponsibleEmail: String? = null,
    val tzDateCreated: String? = Constants.tz,
    val dateCreated: String? = null,
    val createdBy: CreatedBy? = null,
    val sessionId: String? = null,
    val comment: String? = null,
    var editComments: List<UpdateLog> = ArrayList(),
    var dateIdentified: String? = null,
    var dateDue: String? = null,
    var attachments: JsonArray? = null,
    var closed: Boolean = false,
    var minDateIdentified: String? = null,
    var reference: String? = null,

    // Updated in sprint 3.4
    var archived: Boolean = false,

    // Updated CustomValues
    var cusvals: MutableLiveData<List<CustomValue>> = MutableLiveData<List<CustomValue>>(ArrayList()),
    var categoryCusvals: MutableLiveData<List<CustomValue>> = MutableLiveData<List<CustomValue>>(ArrayList()),

    @Expose(serialize = false)
    @Transient
    var categorySetterCallback: Consumer<String>? = null,

    // verifier
    @Expose(serialize = false)
    @Transient
    var dateError: ObservableField<String>? = null,

    var _temp: ForTask? = null,

    @Expose(serialize = false)
    @Transient
    var dueDateError: ObservableField<String>? = null,

    @Expose(serialize = false)
    @Transient
    var personResponsibleError: ObservableField<String>? = null,

    @Expose(serialize = false)
    @Transient
    var personResponsibleEmailError: ObservableField<String>? = null,

    @Expose(serialize = false)
    @Transient
    var personReportError: ObservableField<String>? = null,

    @Expose(serialize = false)
    @Transient
    var descriptionError: ObservableField<String>? = null,

    @Expose(serialize = false)
    @Transient
    var categoryError: ObservableField<String>? = null,

    @Expose(serialize = false)
    @Transient
    var categoryOtherError: ObservableField<String>? = null,

    @Expose(serialize = false)
    @Transient
    var overviewError: ObservableField<String>? = null,

    @Expose(serialize = false)
    @Transient
    var commentError: ObservableField<String>? = null,

    // UIs
    @Expose(serialize = false)
    @Transient
    var isTier5: ObservableBoolean? = null,

    @Expose(serialize = false)
    @Transient
    var isEdit: ObservableBoolean? = null,
    var fileList: List<Uri>? = null,
    private val type: String? = null,

    // Question Id of an inspection when using it inside inspection Signoff
    var questionId: String? = null
)
