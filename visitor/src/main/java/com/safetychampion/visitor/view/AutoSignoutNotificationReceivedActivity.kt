package au.com.safetychampion.scmobile.visitorModule.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import au.com.safetychampion.scmobile.R
import au.com.safetychampion.scmobile.geolocation.GeoLocationUtils
import au.com.safetychampion.scmobile.ui.activities.BaseActivity
import au.com.safetychampion.scmobile.utils.CustomViewLibrary
import au.com.safetychampion.scmobile.visitorModule.vm.AutoSignoutNotificationReceivedVM
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*

/**
 * Created by Minh Khoi MAI on 1/3/21.
 */
class AutoSignoutNotificationReceivedActivity: BaseActivity() {
    companion object {
        const val EXTRA_SIGNOUT_NOTIF_REQUEST = "EXTRA_SIGNOUT_NOTIF_REQUEST"
    }
    val cd = CompositeDisposable()
    val vm: AutoSignoutNotificationReceivedVM by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auto_signout_notification_received)

    }

    override fun onResume() {
        super.onResume()
        handleSignOutRequestFromNotification()
    }


    private fun invokeSignOutFlowFromNotification(requestEvidenceId: String) {
        cd.add(vm.fetchVisitAndIncludeSiteFromDB(evidenceId = requestEvidenceId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({ nEvidence ->
                    CustomViewLibrary.closeProgressBar()
                    if(nEvidence.leave != null) {
                        Toast.makeText(this, "This ${nEvidence.site.tier.VISIT_TERMS.leave.toLowerCase(Locale.ROOT)} request is already completed.", Toast.LENGTH_SHORT).show()
                        nEvidence.notificationId?.let { GeoLocationUtils.removeGeofence(this.applicationContext, it)}
                    } else {
                        VisitorWizardActivity.startSignOutFlow(this,nEvidence.site, nEvidence)
                    }
                    this.finish()
                }, { throwable ->
                    throwable.printStackTrace()
                    Toast.makeText(this, "Unable to Perform Sign Out.", Toast.LENGTH_SHORT).show()
                    this.finish()
                }))

        return
    }

    private fun handleSignOutRequestFromNotification(intent: Intent? = this.intent) {
        val evidenceId = intent?.getStringExtra(EXTRA_SIGNOUT_NOTIF_REQUEST) ?: return
        invokeSignOutFlowFromNotification(evidenceId)
        intent?.putExtra(EXTRA_SIGNOUT_NOTIF_REQUEST, null as String?)
    }
}