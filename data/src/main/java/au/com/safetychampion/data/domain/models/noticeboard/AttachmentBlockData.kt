package au.com.safetychampion.data.domain.models.noticeboard

data class AttachmentBlockData(
    override val type: String?,
    override val _id: String?,
    val description: String? = null,
    val title: String?
) :
    NoticeboardBaseData
