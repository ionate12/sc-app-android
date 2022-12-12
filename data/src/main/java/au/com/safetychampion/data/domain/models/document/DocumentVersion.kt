package au.com.safetychampion.data.domain.models.document

import au.com.safetychampion.data.domain.uncategory.DocAttachment
import com.google.gson.JsonObject
import java.util.*

data class DocumentVersion(
    var category: String? = null,
    var categoryOther: String? = null,
    var createdBy: JsonObject? = null,
    var dateCreated: String? = null,
    var dateIssued: String? = null,
    var description: String? = null,
    var name: String? = null,
    var subcategory: String? = null,
    var subcategoryOther: String? = null,
    var type: String? = null,
    var tzDateCreated: String? = null,
    var copyAt: VersionFrom? = null,
    var vNumber: Int = 0,
    var customVersion: String? = null,
    var attachments: List<DocAttachment>? = null
)
