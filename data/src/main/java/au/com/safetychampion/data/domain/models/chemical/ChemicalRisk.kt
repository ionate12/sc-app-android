package au.com.safetychampion.data.domain.models.chemical

data class ChemicalRisk(
    // Using Boolean Class because there are actually 3 types of data  (true, false and null)
    var assessmentCompleted: Boolean? = null,
    var dangerousGood: Boolean? = null,
    var hazardousSubstance: Boolean? = null,
    var classificationGHS: List<String>? = null
)
