package au.com.safetychampion.data.domain.models

data class Tier(
    val _id: String,
    val type: TierType,
    val name: String? = null,
    val referenceId: String? = null,
    val parent: Tier? = null,
    val city: String? = null,
    val state: String? = null,
    val country: String? = null,
    val address: String? = null,
    val access: Boolean? = null // For morph
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
