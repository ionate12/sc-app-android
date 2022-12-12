package au.com.safetychampion.data.domain.models.document

import au.com.safetychampion.data.domain.models.Tier

data class DocumentCopySource(
    var copyWithoutEdit: Boolean = false,
    var copyableTo: Tier? = null,
    var tier: Tier? = null,
    var type: String? = null,
    var version: DocumentVersion? = null,
    var _id: String? = null
)
