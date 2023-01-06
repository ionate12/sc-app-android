package au.com.safetychampion.data.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Minh Khoi MAI on 9/2/21.
 */
@Parcelize
data class GeofenceData(
//    val latLng: LatLng
    val rad: Long,
    val autoSignOutConfig: Boolean,
    val geoSignInMandatory: Boolean
) : Parcelable {
    init {
//        TODO("latLng")
    }
}

data class GeofenceRequestData(
    val notificationId: Int,
    val evidenceId: String,
    val orgName: String,
    val siteName: String,
    val data: GeofenceData,
    val leaveTerm: String?
)

data class GeoLatLng(val lat: Double, val lon: Double)

enum class GeofenceStatus {
    IS_WITHIN, IS_BOUNDARY, IS_OUT, LOCATION_PERMISSION_DENIED, LOCATION_SETTING_REQUIRE
}
