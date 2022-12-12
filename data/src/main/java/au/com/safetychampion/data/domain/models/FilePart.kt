package au.com.safetychampion.data.domain.models

import android.graphics.Bitmap
import android.net.Uri
import android.util.Pair
import com.google.gson.JsonObject

enum class DataFrom {
    SERVER, OFFLINE, OVERWRITE_OFFLINE
}

/**
 * FilePart:
 * 1. Form: contain all information off the tasks: See TaskEnv's Struct document.
 */

data class FilePart(
    /**
     * Mainly use for json Package when sending the request.
     * It can be used to open the form as well.
     *
     */
    @JvmField
    var form: JsonObject? = null,

    /**
     * Request Data is used for middleman offline Task.
     * Ex: SignOffStart Inspection: requires
     * 1. Data to submit
     * 2. Data for opening the form.
     */
    var requestData: JsonObject? = null,

    var data_from: DataFrom? = null, // Server, Offline, OverwriteOffline

    /**
     * UriList is intented for attachments.
     */
    val uriList: MutableList<Uri>,

    /**
     * BitmapList is signature List of inspections.
     */
    val bitmapList: MutableList<Pair<String, Bitmap>>

)
