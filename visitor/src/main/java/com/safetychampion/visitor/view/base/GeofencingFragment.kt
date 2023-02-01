package au.com.safetychampion.scmobile.visitorModule.view.base

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import au.com.safetychampion.scmobile.R
import au.com.safetychampion.scmobile.exts.setButtonBackgroundColor
import au.com.safetychampion.scmobile.exts.setEnabledWithAlpha
import au.com.safetychampion.scmobile.geolocation.GeoLocationUtils
import au.com.safetychampion.scmobile.geolocation.LocationBaseFragment
import au.com.safetychampion.scmobile.geolocation.LocationPrecondition
import au.com.safetychampion.scmobile.listeners.IOnBackPressed
import au.com.safetychampion.scmobile.visitorModule.models.GeofenceData
import au.com.safetychampion.scmobile.visitorModule.models.GeofenceStatus
import au.com.safetychampion.scmobile.visitorModule.vm.GeofencingVM
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.SupportMapFragment
import kotlinx.android.synthetic.main.fragment_geofencing.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by Minh Khoi MAI on 8/2/21.
 */
open class GeofencingFragment private constructor() : LocationBaseFragment(), IOnBackPressed {
    companion object {
        const val TAG = "GeofencingFragment"
        fun newInstance(data: GeofenceData? = null, viewOnly: Boolean = false): GeofencingFragment {
            val frag = GeofencingFragment()
            val args = Bundle().apply {
                data?.let { putParcelable("data", it) }
                putBoolean("viewOnly", viewOnly)
            }
            frag.arguments = args
            return frag
        }
    }
    var data: Any? = null
    var viewOnly: Boolean = false
    var allowingTapOutsideToDismiss = true
    var mTitle: String = "Site Location-based Sign-in"
    private var mMapView: View? = null
    private val vm: GeofencingVM by viewModels()

    var onPositiveBtnClicked: ((View) -> Unit)? = null
    var onCancelBtnClicked: ((View) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.also {
            val data = it.getParcelable<GeofenceData>("data")
            if (data != null) {
                vm.geofenceData = data
            }
            this.viewOnly = it.getBoolean("viewOnly", false)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_geofencing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initGoogleMapFragment {
            // Geofencing
            mMap!!.addCircle(vm.geofenceData.toCircleOptions())
            mMap!!.addMarker(vm.geofenceData.toMarkerOptions("Your Workplace Location"))
            startRequestMyLocation(false)
            moveCamera(vm.geofenceData.latLng, defaultZoomlevel)
            MainScope().launch {
                delay(2000)
                initObservers()
            }
        }

        if (viewOnly) {
            confirm_button.apply {
                text = "Close"
                setButtonBackgroundColor(R.color.ksr_dark_grey_400)
            }
        } else {
            confirm_button.setEnabledWithAlpha(false)
        }
        confirm_button.setOnClickListener {
            dialog?.dismiss()
            onPositiveBtnClicked?.invoke(it)
        }

        cancel_button.setOnClickListener {
            onCancelBtnClicked?.invoke(it) ?: dialog?.dismiss()
        }

        header_text.text = mTitle
    }

    override fun onCancel(dialog: DialogInterface) {
        cancel_button.performClick()
    }

    override fun onResume() {
        super.onResume()
        displayFullSizeDialog()
        dialog?.setCanceledOnTouchOutside(allowingTapOutsideToDismiss)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return object : Dialog(requireActivity(), theme) {
            override fun onBackPressed() {
                cancel_button.performClick()
            }
        }
    }
    private fun initObservers() {
        if (!viewOnly) {
            try {
                vm.geofenceStatus.observe(viewLifecycleOwner) { status ->
                    displayMessageBox(status)
                    confirm_button.setEnabledWithAlpha(status != GeofenceStatus.IS_OUT || !vm.geofenceData.geoSignInMandatory)
                }
            } catch (e: Exception) {
                // Should catch exception when viewLifecycleOwner == null
                e.printStackTrace()
            }
        }
    }

    fun setTitle(title: String) {
        mTitle = title
        header_text?.text = mTitle
    }

    // MESSAGE BOX
    @SuppressLint("SetTextI18n")
    private fun displayMessageBox(status: GeofenceStatus) {
        request_permission_button.visibility = View.GONE
        when (status) {
            GeofenceStatus.IS_WITHIN -> {
                msg_text.text = "Looks good! You are currently within the site boundary."
                msg_icon_img.setImageResource(R.drawable.ic_check_circle_green_24dp)
            }
            GeofenceStatus.IS_BOUNDARY -> {
                msg_text.text = "Looks like you are at the boundary of the location."
                msg_icon_img.setImageDrawable(null)
            }
            GeofenceStatus.IS_OUT -> {
//                msg_text.text = "Opps, it indicates that you are outside of the location venue. \nDo you want to continue to Sign In?"
                msg_text.text = "Opps, it indicates that you are outside of the location area. \n${ if (vm.geofenceData.geoSignInMandatory) "You must be inside the area to continue the Sign In." else "Do you want to continue to Sign In?"}"
                msg_icon_img.setImageResource(R.drawable.ic_warning_24)
            }
            GeofenceStatus.LOCATION_PERMISSION_DENIED, GeofenceStatus.LOCATION_SETTING_REQUIRE -> {
                if (status == GeofenceStatus.LOCATION_PERMISSION_DENIED) {
                    msg_text.text = "Location permission has been denied. \n${if (vm.geofenceData.geoSignInMandatory) "Please allow Safety Champion to access your location to Sign In." else "Do you want to continue to Sign In?"}"
                    request_permission_button.text = "REQUEST PERMISSION"
                } else {
                    msg_text.text = "Location setting is disabled. \n${if (vm.geofenceData.geoSignInMandatory) "Please allow Safety Champion to access your location to Sign In." else "Do you want to continue to Sign In?"}"
                    request_permission_button.text = "CHANGE LOCATION SETTING"
                }
                msg_icon_img.setImageResource(R.drawable.ic_warning_24)
                request_permission_button.visibility = View.VISIBLE
                request_permission_button.setOnClickListener {
                    checkPreCondition()
                }
            }
        }
        showMsgBoxLayout()
    }

    private fun showMsgBoxLayout() {
        if (msg_layout.visibility == View.GONE) {
            msg_layout.alpha = 0f
            msg_layout.scaleX = 0.2f
            msg_layout.scaleY = 0.2f
            msg_layout.visibility = View.VISIBLE
            msg_layout.animate()
                .setDuration(300)
                .scaleX(1f)
                .scaleY(1f)
                .alpha(0.8f)
                .start()
        }
    }

    // GOOGLE MAPS

    @SuppressLint("MissingPermission")
    private fun initGoogleMapFragment(onReady: () -> Unit) {
        val supportMapFragment = SupportMapFragment.newInstance()
        childFragmentManager.beginTransaction()
            .replace(R.id.support_map_fragment, supportMapFragment)
            .commit()
        supportMapFragment.getMapAsync {
            mMapView = supportMapFragment.view
            mMap = it
            mMap!!.isMyLocationEnabled = GeoLocationUtils.checkLocationPermission(requireContext())
            mMap!!.uiSettings.apply {
                isMyLocationButtonEnabled = true
                isZoomControlsEnabled = true
                isZoomGesturesEnabled = true
                isCompassEnabled = false
            }
            onReady.invoke()
        }
    }

    override fun onLocationCallbackResult(locationResult: LocationResult) {
        vm.calculateGeofencing(locationResult)
    }

    @SuppressLint("MissingPermission")
    override fun onAllPermissionPassed(isPassed: LocationPrecondition) {
        when (isPassed) {
            LocationPrecondition.PASSED -> {
                mMap?.isMyLocationEnabled = true
                startRequestMyLocation(false)
            }
            LocationPrecondition.DENIED -> vm.geofenceStatus.value = GeofenceStatus.LOCATION_PERMISSION_DENIED
            LocationPrecondition.LOCATION_SETTING -> vm.geofenceStatus.value = GeofenceStatus.LOCATION_SETTING_REQUIRE
        }
    }

    override fun onBackPressed(): Boolean {
        cancel_button.performClick()
        return false
    }
}
