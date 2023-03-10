package au.com.safetychampion.data.domain.models.auth

import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.models.TierType

data class MorphPL(
    val target: Target
) : BasePL() {
    data class Target(
        val _id: String,
        val type: TierType
    )

    companion object {
        fun create(id: String) = MorphPL(
            target = Target(id, TierType.T4)
        )
    }
}
