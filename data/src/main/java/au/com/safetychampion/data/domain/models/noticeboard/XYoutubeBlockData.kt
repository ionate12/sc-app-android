package au.com.safetychampion.data.domain.models.noticeboard

data class XYoutubeBlockData(
    override val type: String?,
    override val _id: String?,
    val description: String?,
    val source: String?,
    val title: String?
) : NoticeboardBaseData
