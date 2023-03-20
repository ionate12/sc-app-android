package au.com.safetychampion.data.util.extension

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun Date.toReadableString(format: SCDateFormat): String {
    return format.getFormatter().format(this)
}

enum class SCDateFormat {
    MM_DD_YYYY, YYYY_MM_DD, HH_MM, DD_MM_YYYY_HH_MM, ISO_8601;
    override fun toString(): String = when (this) {
        MM_DD_YYYY -> "MM-dd-yyyy"
        YYYY_MM_DD -> "yyyy-MM-dd"
        HH_MM -> "HH:mm"
        DD_MM_YYYY_HH_MM -> "dd-MM-yyyy HH:mm"
        ISO_8601 -> "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
    }
    fun getFormatter(): DateFormat {
        val format = SimpleDateFormat(this.toString(), Locale.getDefault())
        when (this) {
            ISO_8601 -> format.timeZone = TimeZone.getTimeZone("GMT")
            else -> Unit
        }
        return format
    }
}
