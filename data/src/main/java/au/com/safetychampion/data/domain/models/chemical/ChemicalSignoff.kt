package au.com.safetychampion.data.domain.models.chemical

data class ChemicalSignoff(
    val body: Chemical,
    var task: ChemicalTask = ChemicalTask(),
    val taskId: String,
    val moduleId: String
)
