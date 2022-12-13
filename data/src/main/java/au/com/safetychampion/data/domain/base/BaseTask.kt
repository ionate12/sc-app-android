package au.com.safetychampion.data.domain.base

import au.com.safetychampion.data.domain.models.Tier

interface BaseTask {
    val _id: String
    val type: String
    val _for: BaseModule
    val tier: Tier
    val title: String
    val description: String
    val dateDue: String
    val complete: Boolean
    val inExecution: Boolean
}

interface BaseModule {
    val _id: String
    val type: String
}
