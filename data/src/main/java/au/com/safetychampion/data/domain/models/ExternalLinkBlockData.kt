package au.com.safetychampion.data.domain.models

import au.com.safetychampion.data.domain.models.noticeboard.NoticeboardBaseData
import au.com.safetychampion.data.domain.models.noticeboard.NoticeboardLink

data class ExternalLinkBlockData(
    override val type: String?,
    override val _id: String?,
    val description: String,
    val links: List<NoticeboardLink>?,
    val title: String?
) : NoticeboardBaseData
