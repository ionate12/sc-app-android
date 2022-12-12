package au.com.safetychampion.data.domain.models.noticeboard

import androidx.databinding.ObservableField

data class NoticeboardBlockItem(
    var alpha: Float,
    val id: String,
    val type: String,
    val displayType: Enum<NoticeboardDisplayType>,
    val titleText: String,
    val subTitleText: String?,
    var filePath: String?,
    var documentIcon: Int?,
    val attachmentLink: String?,
    val externalLink: String?,
    var fileName: String?,
    var curState: NoticeboardAttachmentState
) {
    var currentProgress = ObservableField(-1)
    var newProgress: Int = -1
}
