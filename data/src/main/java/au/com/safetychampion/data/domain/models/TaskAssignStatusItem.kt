package au.com.safetychampion.data.domain.models

import au.com.safetychampion.data.domain.base.BaseModule

data class TaskAssignStatusItem(
    override val type: String,
    override val _id: String,
    var assigned: Boolean,
    val isCommentActivated: Boolean,
    var optionalMessage: String? = null,
    val name: String,
    var tier: Tier
) : BaseModule
