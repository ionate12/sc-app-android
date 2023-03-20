package au.com.safetychampion.data.domain.models.action

import au.com.safetychampion.data.domain.Attachment
import au.com.safetychampion.data.domain.Attachment
import au.com.safetychampion.data.domain.base.BaseModule
import au.com.safetychampion.data.domain.base.BaseTask
import au.com.safetychampion.data.domain.models.IAttachment
import au.com.safetychampion.data.domain.models.ICategoryCusval
import au.com.safetychampion.data.domain.models.ICusval
import au.com.safetychampion.data.domain.models.Tier
import au.com.safetychampion.data.domain.models.auth.LoginUser
import au.com.safetychampion.data.domain.models.customvalues.CustomValue

data class ActionTask(
    val attachment: MutableList<Attachment>?,
    val controlLevel: String?,
    val controlLevelOther: String?,
    val completionNotes: String?,
    val changeImplemented: Boolean? = null,
    val dateCompleted: String?,
    val dateSignedoff: String,
    val hazardCategory: String?,
    val hazardCategoryOther: String?,
    val links: MutableList<ActionLink>?,
    val tzDateSignedoff: String?,
    val severity: String?,
    val signedoffBy: LoginUser?,
    override val complete: Boolean?,
    override val dateDue: String?,
    override val description: String?,
    override val _for: BaseModule?,
    override val inExecution: Boolean?,
    override val tier: Tier?,
    override val title: String?,
    override val _id: String,
    override val type: String,
    override var cusvals: MutableList<CustomValue>,
    override var categoryCusvals: MutableList<CustomValue>,
    override var attachments: MutableList<Attachment>
) : BaseTask, ICusval, ICategoryCusval, IAttachment
