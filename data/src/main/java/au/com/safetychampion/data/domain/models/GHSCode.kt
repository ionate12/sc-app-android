package au.com.safetychampion.data.domain.models

import au.com.safetychampion.data.domain.models.chemical.ChemicalCodeCategory

data class GHSCode(
    var title: String? = null,
    var list: List<ChemicalCodeCategory> = arrayListOf()

)
