package au.com.safetychampion.data.domain.models.action

import au.com.safetychampion.data.domain.base.BaseModule
import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.base.BaseTask
import au.com.safetychampion.data.domain.models.Tier
import au.com.safetychampion.data.domain.models.action.network.ActionPL
import au.com.safetychampion.data.domain.models.customvalues.CustomValue
import au.com.safetychampion.data.domain.models.customvalues.ICusval
import au.com.safetychampion.data.domain.models.login.LoginUser
import au.com.safetychampion.data.domain.uncategory.DocAttachment

data class ActionTask(
    override val _id: String?,
    override val type: String?,
    override val _for: BaseModule?,
    override val tier: Tier?,
    override val title: String?,
    override val description: String?,
    override val dateDue: String?,
    override val complete: Boolean?,
    override val inExecution: Boolean?,
    override var cusvals: MutableList<CustomValue>,

    val attachment: List<DocAttachment>,
    val controlLevel: String?,
    val controlLevelOther: String?,
    val completionNotes: String?,
    val changeImplemented: Boolean? = null,
    val dateCompleted: String?,
    val dateSignedoff: String,
    val hazardCategory: String?,
    val hazardCategoryOther: String?,
    val links: MutableList<ActionLink>?,
    val newAction: List<ActionPL>?,
    val tzDateSignedoff: String?,
    val severity: String?,
    val signedoffBy: LoginUser?

) : BasePL(), BaseTask, ICusval
