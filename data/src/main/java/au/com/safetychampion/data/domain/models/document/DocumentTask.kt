package au.com.safetychampion.data.domain.models.document

import au.com.safetychampion.data.domain.Attachment
import au.com.safetychampion.data.domain.base.BaseModule
import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.base.BaseTask
import au.com.safetychampion.data.domain.models.IAttachment
import au.com.safetychampion.data.domain.models.Tier
import java.util.*

data class DocumentTask(
    var category: String? = null,
    var categoryOther: String? = null,
    var copy: Boolean? = false,
    var customVersion: String? = null,
    val dateCompleted: String? = null,
    var dateDueFrom: String = "",
    val dateDueReference: String? = null,
    var dateIssued: String? = null,
    val dateSignedoff: String? = null,
    var name: String? = null,
    val hasNewVersion: Boolean = false,
    val recurrent: Boolean = true,
    val reviewFrequencyKey: String? = null,
    val reviewFrequencyValue: Int? = null,
    var reviewNotes: String? = null,
    var tzDateCreated: String? = TimeZone.getDefault().id,
    val tzDateSignedoff: String? = null,
    val shareWithWorkplace: Boolean = false,
    var subcategory: String? = null,
    var subcategoryOther: String? = null,
    var upVersionFrom: VersionFrom? = VersionFrom(),
    override var type: String? = null,
    override var _id: String? = null,
    override val complete: Boolean = false,
    override var dateDue: String? = null,
    override var description: String? = null,
    override val _for: BaseModule? = null,
    override val inExecution: Boolean? = null,
    override val tier: Tier? = null,
    override val title: String? = null,
    override var attachments: MutableList<Attachment>? = mutableListOf()
) : BasePL(), BaseTask, IAttachment {
    // TODO("tz")
}
