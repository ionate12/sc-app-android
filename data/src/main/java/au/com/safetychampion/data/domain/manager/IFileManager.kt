package au.com.safetychampion.data.domain.manager

import android.net.Uri
import java.io.InputStream

interface IFileManager {
    fun open(uri: Uri): InputStream?
    fun takePersistableUriPermission(
        uri: Uri,
        flags: Int
    )
    suspend fun getDisplayNameFromURI(vararg uris: Uri?): List<String>
}
