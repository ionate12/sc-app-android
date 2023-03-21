package au.com.safetychampion.data.domain.models.chemical

data class GHSCode(
    var title: String? = null,
    var list: List<ChemicalCodeCategory> = arrayListOf()

)
