package au.com.safetychampion.data.domain.models

data class Tier(
    var _id: String,
    var type: TierType,
    val name: String,
    val referenceId: String,
    var parent: Tier? = null,
    var parentTierId: String? = null

)

enum class TierType(val value: String) {
    T1("core.tier.T1"),
    T2("core.tier.T2"),
    T3("core.tier.T3"),
    T4("core.tier.T4"),
    T5("core.tier.T5");
    companion object {
        fun fromString(value: String): TierType {
            return values().firstOrNull { it.value == value } ?: T3
        }
    }
}
