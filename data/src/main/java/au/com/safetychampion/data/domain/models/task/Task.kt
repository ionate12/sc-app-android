package au.com.safetychampion.data.domain.models.task

import au.com.safetychampion.data.domain.base.BaseModuleImpl
import au.com.safetychampion.data.domain.base.BaseTask
import au.com.safetychampion.data.domain.models.Tier
import com.google.gson.annotations.SerializedName

data class Task(
    override val _id: String,
    override val type: String,
    @SerializedName("for")
    override val _for: BaseModuleImpl,
    override val tier: Tier,
    override val title: String,
    override val description: String,
    override val dateDue: String,
    override val complete: Boolean,
    override val inExecution: Boolean
) : BaseTask
