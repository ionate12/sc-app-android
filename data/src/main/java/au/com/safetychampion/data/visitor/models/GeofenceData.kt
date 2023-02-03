package au.com.safetychampion.scmobile.visitorModule.models

import android.os.Parcel
import android.os.Parcelable
import au.com.safetychampion.scmobile.R
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

/**
 * Created by Minh Khoi MAI on 9/2/21.
 */
data class GeofenceData(
    val latLng: LatLng,
    val rad: Long,
    val autoSignOutConfig: Boolean,
    val geoSignInMandatory: Boolean
) : Parcelable {

    constructor(parcel: Parcel) : this (
        parcel.readParcelable(LatLng::class.java.classLoader)!!,
        parcel.readLong(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte()
    )

    fun toCircleOptions(): CircleOptions = CircleOptions().center(latLng).radius(rad.toDouble()).fillColor(R.color.white_alpha_40).strokeColor(R.color.ksr_dark_grey_400).strokeWidth(1f)
    fun toMarkerOptions(title: String? = null): MarkerOptions = MarkerOptions().alpha(0.7f).position(latLng).draggable(false).title(title)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(latLng, flags)
        parcel.writeLong(rad)
        parcel.writeByte(if (autoSignOutConfig) 1 else 0)
        parcel.writeByte(if (geoSignInMandatory) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GeofenceData> {
        override fun createFromParcel(parcel: Parcel): GeofenceData {
            return GeofenceData(parcel)
        }

        override fun newArray(size: Int): Array<GeofenceData?> {
            return arrayOfNulls(size)
        }
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

data class GeoLatLng(val lat: Double, val lon: Double) {
    fun toLatLng() = LatLng(lat, lon)
}

enum class GeofenceStatus {
    IS_WITHIN, IS_BOUNDARY, IS_OUT, LOCATION_PERMISSION_DENIED, LOCATION_SETTING_REQUIRE
}
