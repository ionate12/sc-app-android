package au.com.safetychampion.data.domain.uncategory

data class DocAttachment(
    var _id: String? = null,
    var fileName: String? = null,
    var filePath: String? = null
)

fun List<DocAttachment>.setFilePath(filePath: String) {
    forEach { it.filePath = filePath + "/attachments/" + it._id + "/" + it.fileName }
}
