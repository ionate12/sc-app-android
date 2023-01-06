package au.com.safetychampion.data.domain.models

import au.com.safetychampion.data.domain.models.chemical.ChemicalCodeCategory
import com.google.gson.annotations.SerializedName

data class GHSCode(
    var _id: String? = null,
    @SerializedName("items")
    var categories: List<ChemicalCodeCategory> = arrayListOf()

)
