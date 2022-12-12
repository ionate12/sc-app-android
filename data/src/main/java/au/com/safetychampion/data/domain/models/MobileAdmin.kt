package au.com.safetychampion.data.domain.models

import au.com.safetychampion.data.domain.uncategory.DocAttachment

data class MobileAdmin(
    var _id: String? = null,
    var type: String? = null,
    var category: String? = null,
    var subcategory: String? = null,
    var dateCreated: String? = null,
    var dateActivated: String? = null,
    var values: MobileAdminValue? = null,
    var attachments: List<DocAttachment>? = null,
    var forceHide: Boolean = false,
    var displayDate: String? = null
)
