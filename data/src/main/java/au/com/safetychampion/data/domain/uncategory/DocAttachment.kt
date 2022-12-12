package au.com.safetychampion.data.domain.uncategory

data class DocAttachment(
    var dateUploaded: String? = null,
    var _id: String? = null,
    var fileName: String? = null,
    var group: String? = null,
    var size: Long? = null,
    var type: String? = null,
    var download: String? = null,
    var isDownloaded: Boolean? = null,
    var vDoc: Int? = null,
    var filePath: String? = null,
    var delete: Boolean? = null,
    var fullUrl: String? = null
)
