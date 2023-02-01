package au.com.safetychampion.scmobile.visitorModule.vm

import android.location.Location
import android.util.Log
import androidx.lifecycle.MutableLiveData
import au.com.safetychampion.scmobile.BaseViewModel
import au.com.safetychampion.scmobile.visitorModule.models.GeofenceData
import au.com.safetychampion.scmobile.visitorModule.models.GeofenceStatus
import au.com.safetychampion.scmobile.visitorModule.view.base.GeofencingFragment
import com.google.android.gms.location.LocationResult

/**
 * Created by Minh Khoi MAI on 9/2/21.
 */
class GeofencingVM : BaseViewModel() {
    private var RELATIVE_RAD: Double = 50.0 // in meter
    lateinit var geofenceData: GeofenceData
    var geofenceStatus: MutableLiveData<GeofenceStatus> = MutableLiveData()

    // Indicate
    fun calculateGeofencing(locationResult: LocationResult): Boolean {
        locationResult.lastLocation ?: return false
        val acuracy = locationResult.lastLocation!!.accuracy
        val lat = locationResult.lastLocation!!.latitude
        val lng = locationResult.lastLocation!!.longitude
        val results = FloatArray(1) { -1f }
        Location.distanceBetween(geofenceData.latLng.latitude, geofenceData.latLng.longitude, lat, lng, results)
        Log.d(GeofencingFragment.TAG, "calculateGeofencing: \nAcuracy: $acuracy\nLat: $lat\n Lng: $lng \nResults: ${results[0]}")
        val distance = results[0]
        if (distance > 0) {
            val mDiff = geofenceData.rad - distance
            var status: GeofenceStatus = GeofenceStatus.IS_WITHIN
            if (mDiff < -RELATIVE_RAD) status = GeofenceStatus.IS_OUT
            if (mDiff > -RELATIVE_RAD && mDiff < RELATIVE_RAD) status = GeofenceStatus.IS_BOUNDARY
            if (mDiff > RELATIVE_RAD) status = GeofenceStatus.IS_WITHIN

            Log.d(
                GeofencingFragment.TAG,
                "\nmDiff: $mDiff " +
                    "\nisWithin: ${status == GeofenceStatus.IS_WITHIN}" +
                    "\n isInBoundary: ${status == GeofenceStatus.IS_BOUNDARY} " +
                    "\nisOut: ${status == GeofenceStatus.IS_OUT}"
            )

            if (status != geofenceStatus.value) {
                geofenceStatus.value = status
            }
        }
        return results.isNotEmpty()
    }
}
