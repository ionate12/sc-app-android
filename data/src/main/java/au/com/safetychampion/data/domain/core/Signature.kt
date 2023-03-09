package au.com.safetychampion.data.domain.core

import android.graphics.Bitmap
import au.com.safetychampion.data.domain.models.TimeField
import java.util.*

open class Signature(
    val bitmap: Bitmap?,
    var name: String,
    //region for Incident Signature
    val role: String? = null,
    val roleOther: String? = null,
    val date: Date? = null,
    val time: TimeField? = null,
    val tz: String? = null
) {
    val _id: String by lazy { UUID.randomUUID().toString() }
    fun payloadName() = "signature_${name}_$_id.png"
}
