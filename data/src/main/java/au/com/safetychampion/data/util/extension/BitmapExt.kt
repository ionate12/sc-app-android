package au.com.safetychampion.data.util.extension

import android.graphics.Bitmap
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream

fun Bitmap.toInputStream(): ByteArray {
    val arrOut = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG, 100, arrOut)
    return arrOut.toByteArray()
}

fun Bitmap.toMultiPart(part: String, fileName: String): MultipartBody.Part {
    val byte = toInputStream()
    val body = byte.toRequestBody(
        "image/jpg".toMediaType()
    )
    return MultipartBody.Part.createFormData(part, fileName, body)
}
