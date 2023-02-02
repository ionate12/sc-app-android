package au.com.safetychampion.data.domain.manager

import android.net.Uri
import java.io.InputStream

interface IFileManager {
    fun open(uri: Uri): InputStream?
    fun takePersistableUriPermission(
        uri: Uri,
        flags: Int
    )
    suspend fun getDisplayNameFromURI(uris: List<Uri>): List<String>

    val externalFilesDir: String
}
