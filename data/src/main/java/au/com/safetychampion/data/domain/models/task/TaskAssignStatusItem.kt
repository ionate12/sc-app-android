package au.com.safetychampion.data.domain.models.task

import au.com.safetychampion.data.domain.base.BaseModule
import au.com.safetychampion.data.domain.models.Tier

data class TaskAssignStatusItem(
    override val type: String,
    override val _id: String,
    var assigned: Boolean,
    val isCommentActivated: Boolean,
    var optionalMessage: String? = null,
    val name: String,
    var tier: Tier
) : BaseModule
