package au.com.safetychampion.data.domain.models

import au.com.safetychampion.data.domain.base.BaseModule

data class TaskAssignStatusItem(
    var assigned: Boolean = false,
    val name: String,
    var tier: Tier,
    override val type: String,
    override val _id: String
) : BaseModule
