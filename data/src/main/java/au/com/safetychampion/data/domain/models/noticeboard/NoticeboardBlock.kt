package au.com.safetychampion.data.domain.models.noticeboard

data class NoticeboardBlock(val type: String?, val data: NoticeboardBaseData?) {
    companion object {
        const val TYPE_VDOC = "application/vnd.safetychampion.core_module_vdoc"
        const val TYPE_ATTACHMENT = "application/vnd.safetychampion.attachment"
        const val TYPE_YOUTUBE = "video/x-youtube"
        const val TYPE_EXTERNAL_LINK = "application/vnd.safetychampion.externalLinks"
    }
}
