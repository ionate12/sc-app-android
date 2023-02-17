package au.com.safetychampion.data.data.noticeboard

import au.com.safetychampion.data.domain.models.Tier
import au.com.safetychampion.data.domain.models.UpdateBy
import au.com.safetychampion.data.domain.models.contractor.Contact
import au.com.safetychampion.data.domain.models.noticeboard.NoticeboardBlock
import au.com.safetychampion.data.domain.uncategory.DocAttachment

data class NoticeboardBlockList(
    val attachments: MutableList<DocAttachment>? = null,
    val blocks: MutableList<NoticeboardBlock>? = null,
    val board: Tier? = null,
    val contacts: MutableList<Contact>? = null,
    val dateUpdated: String? = null,
    val forms: MutableList<Tier>? = null,
    val tier: Tier? = null,
    val type: String? = null,
    val tzDateUpdated: String? = null,
    val updatedBy: UpdateBy? = null,
    val _id: String? = null
)
