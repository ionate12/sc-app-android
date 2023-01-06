package au.com.safetychampion.data.domain.models.chemical

data class ChemicalLocation(
    var key: String? = null,
    var main: String? = null,
    var mainOther: String? = null,
    var qAvg: Int = 0,
    var qMax: Int = 0,
    var sub: String? = null,
    var subOther: String? = null,
    var wpName: String? = null
)
