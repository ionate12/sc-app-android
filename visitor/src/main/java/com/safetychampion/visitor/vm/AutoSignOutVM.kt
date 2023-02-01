package au.com.safetychampion.scmobile.visitorModule.vm

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import au.com.safetychampion.scmobile.BaseViewModel
import au.com.safetychampion.scmobile.geolocation.GeoLocationUtils
import au.com.safetychampion.scmobile.geolocation.LocationPrecondition
import au.com.safetychampion.scmobile.utils.NotificationUtils
import au.com.safetychampion.scmobile.utils.RxUtils
import au.com.safetychampion.scmobile.utils.SharedPrefCollection
import au.com.safetychampion.scmobile.visitorModule.VisitorRepository
import au.com.safetychampion.scmobile.visitorModule.models.GeofenceRequestData
import au.com.safetychampion.scmobile.visitorModule.models.VisitorEvidence
import au.com.safetychampion.scmobile.visitorModule.models.VisitorMockData
import com.google.android.gms.location.LocationServices

/**
 * Created by Minh Khoi MAI on 25/2/21.
 */
class AutoSignOutVM: BaseViewModel() {
    val repo = VisitorRepository
    var triggerGeofenceRequests = false
    @SuppressLint("MissingPermission")
    fun requestAutoSignOut(context: Context, fullList: List<VisitorEvidence>, onComplete: () -> Unit, onFailure: (String) -> Unit) {
        if (!triggerGeofenceRequests) return
        //Store geofence request data.
        val geofenceRequestData = getGeofenceRequests(fullList)
        if (geofenceRequestData.isEmpty()) return


        //Permission
        val permissionApprove = GeoLocationUtils.checkGeofencePreConditions(context) == LocationPrecondition.PASSED
        if (!permissionApprove) {
            onFailure("PERMISSION_REQUIRED")
            return
        }

        // pre-condition ends.

        val geofenceRequest = GeoLocationUtils.createGeofenceRequest(geofenceRequestData)

        val pendingIntent = GeoLocationUtils.getGeofencingPendingIntent(context)
        NotificationUtils.createGeofenceChannel(context.applicationContext)

        val geofencingClient = LocationServices.getGeofencingClient(context.applicationContext)
        geofencingClient.removeGeofences(pendingIntent)?.run {
            addOnCompleteListener {
                geofencingClient.addGeofences(geofenceRequest, pendingIntent)?.run {
                    addOnSuccessListener {
                        SharedPrefCollection.setGeofenceRequests(geofenceRequestData)
                        triggerGeofenceRequests = false
                        onComplete.invoke()

                    }
                    addOnFailureListener {
                        onFailure("GEOFENCE_ADD_FAILURE")
                        Toast.makeText(context, "ERROR.Geofence cannot be added", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
    }

    private fun getGeofenceRequests(fullList: List<VisitorEvidence>): List<GeofenceRequestData> {
        val filteredList = fullList.filter { it.isAutoSignOutActive && it.leave == null && it.notificationId != null}
        if (filteredList.isEmpty()) return emptyList()
        return filteredList.map {
            val nEvidence = RxUtils.blockingGet(repo.getFullEvidenceById(it._id))
            return@map nEvidence
        }.mapNotNull {
            val geofenceData = it?.site?.getGeofenceData() ?: return@mapNotNull null
            GeofenceRequestData(it.notificationId!!, it._id, it.site.tier.name, it.site.title, geofenceData, it.site.tier.VISIT_TERMS.leave)
        }
    }

    fun requestAutoSignOut(context: Context, onComplete: () -> Unit, onFailure: (String) -> Unit) {
        val fullList = repo.getActivitiesByProfileId(VisitorMockData.mockProfileId).take(1).blockingFirst() ?: return
        requestAutoSignOut(context, fullList.map { it.toVisitorEvidence() }, onComplete, onFailure)
    }
}