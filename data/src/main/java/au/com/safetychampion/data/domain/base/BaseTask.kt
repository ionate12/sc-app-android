package au.com.safetychampion.data.domain.base

import au.com.safetychampion.data.domain.Tier
import java.time.OffsetDateTime

interface BaseTask {
    val _id: String
    val type: String
    val _for: BaseModule
    val tier: Tier
    val title: String
    val description: String
    val dateDue: OffsetDateTime
    val complete: Boolean
    val inExecution: Boolean
}

interface BaseModule {
    val _id: String
    val type: String
}