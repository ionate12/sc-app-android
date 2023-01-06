package au.com.safetychampion.data.domain.models.chemical

import com.google.gson.annotations.SerializedName

data class ChemicalTransport(
    @SerializedName("class")
    var clazz: String? = null,
    var packingGroup: String? = null,
    var packingGroupOther: String? = null,
    var subRisks: List<String>? = null,
    var subRisksOther: List<String>? = null,
    var unNumber: String? = null
)
