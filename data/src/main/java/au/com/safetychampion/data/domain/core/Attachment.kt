package au.com.safetychampion.data.domain

import android.content.Intent
import android.net.Uri
import au.com.safetychampion.data.domain.manager.IFileManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.apache.commons.io.IOUtils

sealed class Attachment {
    data class New(val uri: Uri, val group: String?) : Attachment()
    data class Review(
        val _id: String,
        val type: String,
        val size: Long,
        val fileName: String,
        val download: String,
        val dateUploaded: String?,
        val group: String?,
        var delete: Boolean = false
    ) : Attachment()
}

// data class Attachment(
//    val _id: String?,
//    val file: Uri,
//    val type: String,
//    val displayName: String? = "no-name",
//    val partName: String,
//    val group: String?,
//    val delete: Boolean = false
// )

suspend fun List<Attachment.New>.toMultipartBody(fileManager: IFileManager): List<MultipartBody.Part> {
    return withContext(Dispatchers.IO) {
        val parts = mutableListOf<MultipartBody.Part>()
        for (i in this@toMultipartBody.indices) {
            val att = this@toMultipartBody[i]
            if (
                att.uri.pathSegments.contains("my_files") ||
                att.uri.pathSegments.contains("my_images")
            ) {
                fileManager
                    .takePersistableUriPermission(
                        uri = att.uri,
                        flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or
                            Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                    )
            }
            MultipartBody.Part.createFormData(
                name = i.toString(),
                filename = fileManager.getFileName(att.uri),
                body = fileManager
                    .open(att.uri)
                    .use(IOUtils::toByteArray)
                    .toRequestBody()
            ).let { parts.add(it) }
        }
        parts
    }
}
//
// suspend fun List<Attachment>.export(fileManager: IFileManager): Pair<List<DocAttachment>, List<Uri>> {
//    val uris = this@export.mapTo(mutableListOf()) { it.file }
//    val groups = this@export.map { it.group }
//    val attachmentsName = fileManager.getDisplayNameFromURI(uris)
//    val docAttachments = mutableListOf<DocAttachment>()
//
//    for (i in attachmentsName.indices) {
//        val name = attachmentsName[i]
//        if (name.trim().isEmpty()) {
//            uris.removeAt(i)
//            continue
//        }
//        docAttachments.add(
//            DocAttachment(
//                fileName = name,
//                group = if (!groups[i].isNullOrEmpty()) groups[i] else null
//            )
//        )
//    }
//    return Pair(docAttachments, uris)
// }
