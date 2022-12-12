package au.com.safetychampion.scmobile.modules.incident.model

class ConfiguredLocations(var byTier: MutableList<ConfLocationTier> = mutableListOf())

data class ConfLocationTier(
    val type: String,
    val _id: String,
    val configuredLocations: List<String>
)
