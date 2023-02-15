package au.com.safetychampion.data.domain.core

import android.graphics.Bitmap
import java.util.UUID

data class Signature(
    val bitmap: Bitmap,
    var name: String // TODO: Add Role for incident module
) {
    val _id: String by lazy { UUID.randomUUID().toString() }
    fun payloadName() = "signature_${name}_$_id.png"
}
