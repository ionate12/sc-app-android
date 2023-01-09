package au.com.safetychampion.data.domain

import android.content.Intent
import android.net.Uri
import au.com.safetychampion.data.domain.manager.IFileManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.apache.commons.io.IOUtils

data class Attachment(
    val file: Uri,
    val type: String,
    val displayName: String? = "no-name",
    val partName: String
)

suspend fun List<Attachment>?.toMultipartBody(fileManager: IFileManager): List<MultipartBody.Part> {
    return withContext(Dispatchers.IO) {
        this@toMultipartBody?.map { att ->
            if (
                att.file
                    .pathSegments
                    .contains("my_files") || att.file
                    .pathSegments
                    .contains("my_images")
            ) {
                fileManager
                    .takePersistableUriPermission(
                        uri = att.file,
                        flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                    )
            }
            MultipartBody.Part.createFormData(
                name = att.partName,
                filename = att.displayName,
                body = fileManager
                    .open(att.file)
                    .use(IOUtils::toByteArray)
                    .toRequestBody()
            )
        } ?: emptyList()
    }
}
