package au.com.safetychampion.data.domain.models.incidents

import androidx.databinding.BaseObservable
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableList
import androidx.lifecycle.MutableLiveData
import au.com.safetychampion.data.domain.models.TimeField
import au.com.safetychampion.data.domain.models.customvalues.CustomValue

data class Incident(
    /**
     * This is a payload package for submission;
     */
    @JvmField
    var sessionId: String? = null,
    @JvmField
    val tzDateCreated: ObservableField<String>? = null,
    @JvmField
    val dateOccurred: ObservableField<String>? = null,
    @JvmField
    val timeOccurred: ObservableField<TimeField>? = null,
    @JvmField
    val overview: ObservableField<String>? = null,
    @JvmField
    val description: ObservableField<String>? = null,

    // Category getData from configuration.
    @JvmField
    val category: ObservableField<String>? = null,
    @JvmField
    val categoryOther: ObservableField<String>? = null,

    // Location getData from configuration.
    @JvmField
    val location: ObservableField<String>? = null,
    @JvmField
    val locationOther: ObservableField<String>? = null,
    @Transient
    val displayLocationOther: ObservableBoolean = ObservableBoolean(false),
    @JvmField
    val personReporting: ObservableField<String>? = null,
    @JvmField
    val witnessPhone: ObservableField<String>? = null,
    @JvmField
    val witnessName: ObservableField<String>? = null,

    // Injury
    @JvmField
    val injuredPersonName: ObservableField<String>? = null,
    @JvmField
    val injuredPersonPhone: ObservableField<String>? = null,
    @JvmField
    val injuredPersonRole: ObservableField<String>? = null,
    @JvmField
    val injuredPersonRoleOther: ObservableField<String>? = null,
    @JvmField
    val injuryDescription: ObservableField<String>? = null,
    @JvmField
    val propOrEnvDamage: ObservableBoolean? = null,

    @Transient
    val propOrEnvDamageString: ObservableField<String>? = null,
    @JvmField
    val propOrEnvDamageDescription: ObservableField<String>? = null,
    @JvmField
    val injuredBodyParts: ObservableList<InjuredBodyPart>? = null,
    @JvmField
    val cusvals: MutableLiveData<List<CustomValue>>? = null,
    @JvmField
    val propOrEnvDamageCusvals: MutableLiveData<List<CustomValue>>? = null,
    @JvmField
    val categoryCusvals: MutableLiveData<List<CustomValue>>? = null,
    @JvmField
    val injuredPersonLinks: MutableLiveData<List<InjuredPersonLink>>? = null
) : BaseObservable()
