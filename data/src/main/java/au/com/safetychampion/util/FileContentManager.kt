package au.com.safetychampion.util

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream

interface IFileManager {
    fun open(uri: Uri): InputStream?
    fun takePersistableUriPermission(
        uri: Uri,
        flags: Int
    )
    suspend fun getDisplayNameFromURI(vararg uris: Uri?): List<String>
}

class FileContentManager(
    context: Context
) : IFileManager {
    private val contentResolver by lazy { context.contentResolver }

    override fun open(uri: Uri): InputStream? {
        return contentResolver.openInputStream(uri)
    }

    override fun takePersistableUriPermission(uri: Uri, flags: Int) {
        contentResolver.takePersistableUriPermission(uri, flags)
    }

    override suspend fun getDisplayNameFromURI(vararg uris: Uri?): List<String> {
        return withContext(Dispatchers.IO) {
            uris.indices.map { i ->
                contentResolver
                    .query(
                        uris[i]!!,
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
}
