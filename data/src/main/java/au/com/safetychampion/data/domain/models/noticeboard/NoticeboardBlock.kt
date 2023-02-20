package au.com.safetychampion.data.domain.models.noticeboard

const val TYPE_VDOC = "application/vnd.safetychampion.core_module_vdoc"
const val TYPE_ATTACHMENT = "application/vnd.safetychampion.attachment"
const val TYPE_YOUTUBE = "video/x-youtube"
const val TYPE_EXTERNAL_LINK = "application/vnd.safetychampion.externalLinks"

sealed interface BaseBlockData {
    val type: String?
    val _id: String?
}

data class NoticeboardBlock(
    val type: String?,
    val data: BaseBlockData?
)
data class BlockData(
    override val type: String?,
    override val _id: String?,
    val description: String? = null,
    val title: String?
) : BaseBlockData

data class VDocBlockData(
    override val type: String?,
    override val _id: String?
) : BaseBlockData

data class XYoutubeBlockData(
    override val type: String?,
    override val _id: String?,
    val description: String?,
    val source: String?,
    val title: String?
) : BaseBlockData

data class ExternalLinkBlockData(
    override val type: String?,
    override val _id: String?,
    val description: String,
    val links: List<NoticeboardLink>?,
    val title: String?
) : BaseBlockData

data class NoticeboardLink(val title: String?, val uri: String?)
