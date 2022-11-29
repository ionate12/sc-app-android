package au.com.safetychampion.data.domain

import au.com.safetychampion.data.domain.base.BaseModule
import au.com.safetychampion.data.domain.base.BaseTask
import java.time.OffsetDateTime

data class Task(
    override val _id: String,
    override val type: String,
    override val _for: BaseModule,
    override val tier: Tier,
    override val title: String,
    override val description: String,
    override val dateDue: OffsetDateTime,
    override val complete: Boolean,
    override val inExecution: Boolean
) : BaseTask
