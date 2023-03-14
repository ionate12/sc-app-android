package au.com.safetychampion.data.domain.models.chemical

import au.com.safetychampion.data.domain.Attachment
import au.com.safetychampion.data.domain.base.BaseModule
import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.base.BaseTask
import au.com.safetychampion.data.domain.models.Tier
import au.com.safetychampion.data.domain.uncategory.Constants

data class ChemicalTask(
    val attachments: MutableList<Attachment> = mutableListOf(),
    var completionNotes: String? = null,
    var dateCompleted: String? = null,
    var dateSdsIssued: String? = null,
    var dateToReview: String? = null,
    var hasSds: Boolean = true,
    var name: String? = null,
    var purpose: String? = null,
    var recurrent: Boolean = true,
    var tzDateSignedoff: String? = Constants.tz,
    override val complete: Boolean? = false,
    override val dateDue: String? = null,
    override val description: String? = null,
    override val _for: BaseModule? = null,
    override val inExecution: Boolean? = null,
    override val tier: Tier? = null,
    override val title: String? = null,
    override val _id: String,
    override val type: String? = null
) : BasePL(), BaseTask
