package au.com.safetychampion.scmobile.visitorModule.view

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.navigation.fragment.navArgs
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import au.com.safetychampion.scmobile.R
import au.com.safetychampion.scmobile.SCBaseFragment
import au.com.safetychampion.scmobile.geolocation.GeoLocationUtils
import au.com.safetychampion.scmobile.geolocation.LocationPrecondition
import au.com.safetychampion.scmobile.ui.activities.BaseActivity
import au.com.safetychampion.scmobile.ui.activities.IOnPermissionCallback
import au.com.safetychampion.scmobile.utils.GsonHelper
import au.com.safetychampion.scmobile.utils.SCPermissionType
import au.com.safetychampion.scmobile.utils.SharedPrefCollection
import au.com.safetychampion.scmobile.visitorModule.VisitorRepository
import au.com.safetychampion.scmobile.visitorModule.models.VisitorEvidence
import com.bumptech.glide.Glide
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.dialog_fragment_visitor_auto_signout.*
import java.util.*

/**
 * Created by Minh Khoi MAI on 23/2/21.
 */
class GeoLeaveDialogFragment : SCBaseFragment(), IOnPermissionCallback {
    private val args: GeoLeaveDialogFragmentArgs by navArgs()
    companion object {
        fun newInstance(evidence: VisitorEvidence): GeoLeaveDialogFragment {
            return GeoLeaveDialogFragment().apply { this.evidence = evidence }
        }
        const val TAG = "AutoSignOutDialogFragment"
        const val AUTO_SIGNOUT_TITLE = "Location-based Sign-out"
    }

    lateinit var evidence: VisitorEvidence
    var onPositiveBtnClicked: (() -> Unit)? = null
    var isRequestingPermission = false
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_fragment_visitor_auto_signout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun onResume() {
        super.onResume()
        preventDismissOnTouchOutside()
    }

    private fun initViews() {
        possitive_btn.setOnClickListener { proceed() }
        negative_btn.setOnClickListener { cancel() }
        loadImg()
        val title = AUTO_SIGNOUT_TITLE.toLowerCase(Locale.ROOT)
        txt_content.text = Html.fromHtml("<p>The $title feature will notify you when you leave the site area.</p> <p>To enable this feature, Safety Champion might require access to your device location while you are signed in at a site.</p> <p> Do you want to activate the $title for ${evidence.getOrgAndTitle()}?</p>", HtmlCompat.FROM_HTML_MODE_LEGACY)
    }

    private fun loadImg() {
        val progress = CircularProgressDrawable(requireContext()).apply {
            strokeWidth = 5f
            centerRadius = 30f
            start()
        }
        Glide.with(this)
            .load("https://developer.android.com/images/training/geofence.png")
            .placeholder(progress)
            .into(geofence_img)
    }

    private fun proceed() {
        // Check and Ask for permission
        if (!isPermissionApproved()) {
            requestPermissions()
        } else if (GeoLocationUtils.didReachGeofenceLimit()) {
            GeoLocationUtils.showOverGeofenceLimitDialog(requireActivity() as AppCompatActivity)
            dialog?.dismiss()
        } else {
            performSetAutoSignoutAndDismiss()
        }
    }

    private fun performSetAutoSignoutAndDismiss() {
        cd.add(
            VisitorRepository.setAutoSignOut(evidence._id, true)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    SharedPrefCollection.geofenceAutoSignOutFlag = auto_signout_flag_switch.isChecked
                    dismiss()
                    onPositiveBtnClicked?.invoke()
                    Toast.makeText(context, "You have successfully activated $AUTO_SIGNOUT_TITLE for ${evidence.getOrgAndTitle()}", Toast.LENGTH_SHORT).show()
                }, { throwable ->
                    dismiss()
                    throwable.printStackTrace()
                })
        )
    }

    private fun cancel() {
        dialog?.dismiss()
    }

    private fun isPermissionApproved(): Boolean {
        return GeoLocationUtils.checkGeofencePreConditions(requireContext()) == LocationPrecondition.PASSED
    }

    private fun requestPermissions() {
        require(requireActivity() is BaseActivity)
        val baseActivity = requireActivity() as BaseActivity
        GeoLocationUtils.requestForGeofencePreConditions(baseActivity) {
            if (isPermissionApproved()) {
                performSetAutoSignoutAndDismiss()
            } else {
                Toast.makeText(requireContext(), "Cannot start. Location access permission needs to be allowed All The Time to activate the $AUTO_SIGNOUT_TITLE.", Toast.LENGTH_LONG).show()
            }
        }
        isRequestingPermission = true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (isRequestingPermission) {
            outState.putString("CURRENT_EVIDENCE", GsonHelper.getGson().toJson(evidence))
            (activity as? VisitorMainActivity)?.registerToSaveInstanceState = Pair(TAG, outState)
            isRequestingPermission = false
        }
    }

    override fun invoke(requestCode: Int, result: Map<String, SCPermissionType>) {

    }
}
