package au.com.safetychampion.util

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.net.Uri
import android.provider.MediaStore
import au.com.safetychampion.data.domain.manager.IFileManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream

class FileContentManager(
    private val contentResolver: ContentResolver,
    override val externalFilesDir: String
) : IFileManager {
    override fun open(uri: Uri): InputStream? {
        return contentResolver.openInputStream(uri)
    }

    override fun takePersistableUriPermission(uri: Uri, flags: Int) {
        contentResolver.takePersistableUriPermission(uri, flags)
    }

    @SuppressLint("Range")
    override suspend fun getDisplayNameFromURI(uris: List<Uri>): List<String> {
        return withContext(Dispatchers.IO) {
            uris.indices.map { i ->
                contentResolver
                    .query(
                        uris[i],
                        null,
                        null,
                        null,
                        null
                    )?.use {
                        it.moveToFirst()
                        it.getString(
                            it.getColumnIndex(
                                MediaStore.Images.Media.DISPLAY_NAME
                            )
                        )
                    } ?: ""
            }
        }
    }

    override fun getFileName(uri: Uri) = contentResolver
        .query(
            uri,
            null,
            null,
            null,
            null
        )?.use {
            val columnIndex = it.getColumnIndex(
                MediaStore.Images.Media.DISPLAY_NAME
            )
            if (columnIndex < 0) return@use ""
            it.moveToFirst()
            it.getString(columnIndex)
        } ?: ""

    override fun getFileType(uri: Uri) = contentResolver.getType(uri)
}

data class UriInfo(val fileName: String, val type: String?)
