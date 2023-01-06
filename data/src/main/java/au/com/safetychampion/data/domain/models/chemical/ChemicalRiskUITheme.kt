package au.com.safetychampion.data.domain.models.chemical

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import java.util.ArrayList

data class ChemicalRiskUITheme(
    val physicalTitle: String = "Physical Properties",
    val healthTitle: String = "Health Properties",
    val envTitle: String = "Environmental Properties",
    var locations: Int = 0,
    var physical: ObservableInt? = null,
    var health: ObservableInt? = null,
    var environment: ObservableInt? = null,
    var hazardous: ObservableInt? = null,
    var dangerous: ObservableInt? = null,
    var riskVisibility: ObservableBoolean = ObservableBoolean(true),
    var selectedGHSCodes: List<ChemicalCodeCategory> = ArrayList()
)
