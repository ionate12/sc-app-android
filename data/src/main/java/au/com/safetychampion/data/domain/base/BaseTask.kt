package au.com.safetychampion.data.domain.base

import au.com.safetychampion.data.domain.models.Tier

interface BaseTask : BaseModule {
    val complete: Boolean?
    val dateDue: String?
    val description: String?
    val _for: BaseModule?
    val inExecution: Boolean?
    val tier: Tier?
    val title: String?
    override val _id: String?
    override val type: String?
}

interface BaseModule {
    val _id: String?
    val type: String?
}

class BaseModuleImpl(override val _id: String, override val type: String) : BaseModule
