package au.com.safetychampion.scmobile.visitorModule.view

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import au.com.safetychampion.scmobile.R
import au.com.safetychampion.scmobile.SCBaseFragment
import au.com.safetychampion.scmobile.constantValues.Constants
import au.com.safetychampion.scmobile.databinding.FragmentVisitorEvidenceDetailBinding
import au.com.safetychampion.scmobile.exceptions.ErrorEnvThrowable
import au.com.safetychampion.scmobile.geolocation.GeoLocationUtils
import au.com.safetychampion.scmobile.geolocation.LocationPrecondition
import au.com.safetychampion.scmobile.modules.incident.form.CusvalView
import au.com.safetychampion.scmobile.ui.activities.BaseActivity
import au.com.safetychampion.scmobile.ui.dialogs.SCAlertDialogFragment
import au.com.safetychampion.scmobile.utils.CustomViewLibrary
import au.com.safetychampion.scmobile.utils.DialogHelper
import au.com.safetychampion.scmobile.visitorModule.models.VisitorEvidence
import au.com.safetychampion.scmobile.visitorModule.models.VisitorSite
import au.com.safetychampion.scmobile.visitorModule.view.base.GeofencingFragment
import au.com.safetychampion.scmobile.visitorModule.vm.AutoSignOutVM
import au.com.safetychampion.scmobile.visitorModule.vm.VisitorEvidenceDetailVM
import com.google.android.material.transition.MaterialContainerTransform
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by @Minh_Khoi_MAI on 13/01/21
 */
class VisitorEvidenceDetailFragment : SCBaseFragment() {
    companion object {
        const val TAG = "VisitorEvidenceDetailFragment"
    }
    lateinit var binding: FragmentVisitorEvidenceDetailBinding
    val vm: VisitorEvidenceDetailVM by viewModels()
    val autoSignOutVM: AutoSignOutVM by viewModels()
    var isRequestingPermission = false
    private val args: VisitorEvidenceDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.container
            duration = Constants.MD_MOTION_DURATION
            scrimColor = Color.TRANSPARENT
            setAllContainerColors(requireContext().getColor(R.color.light_grey))
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentVisitorEvidenceDetailBinding.inflate(inflater, null, false)
        binding.main = this
        binding.lifecycleOwner = this
        initOservers()
//        Handler(Looper.getMainLooper()).postDelayed({showLoadingOverlay()}, 300)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as? VisitorMainActivity)?.apply {
            this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        loadEvidence()
    }

    private fun loadEvidence() {
        if (binding.loadingOverlay.visibility == View.GONE) {
            CustomViewLibrary.startProgressBar(binding.root.rootView)
        }
        vm.getEvidence(args.evidenceId)
    }

    private fun initOservers() {
        vm.evidence.observe(viewLifecycleOwner) { evidence ->
            bindForm(evidence)
        }
        vm.errorMessage.observe(viewLifecycleOwner) { errorString ->
            if (errorString == null) return@observe
            hideLoadingOverlay()
            CustomViewLibrary.closeProgressBar()
            val alert = SCAlertDialogFragment.newInstance("Oops!!", "Something went wrong/nCannot open Visit Detail.", SCAlertDialogFragment.AlertType.Failure)
            alert.onDismissListener = {
                findNavController().popBackStack()
            }
            vm.errorMessage.postValue(null)
        }

        vm.autoSignOutCheck.observe(viewLifecycleOwner) { isChecked ->
            fun requestAutoSignOut() {
                vm.setAutoSignOut(isChecked)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            if (!isChecked) {
                                vm.evidence.value?.notificationId?.let {
                                    GeoLocationUtils.removeGeofence(requireContext(), it)
                                    Log.d("VisitorEvidenceDetailFragment", "AUTO SIGNOUT_SWITCH: ")
                                    vm.evidence.value!!.isAutoSignOutActive = isChecked
                                }
                            } else {
                                autoSignOutVM.triggerGeofenceRequests = true
                                autoSignOutVM.requestAutoSignOut(requireContext(), {
                                    vm.evidence.value!!.isAutoSignOutActive = isChecked
                                    vm.autoSignOutCheck.value = true
                                    Toast.makeText(requireContext(), "Geofence has been added.", Toast.LENGTH_SHORT).show()
                                }, { errorCode ->
                                    Toast.makeText(requireContext(), "Set Geofencing Failed", Toast.LENGTH_SHORT).show()
                                    vm.setAutoSignOut(!isChecked).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe()
                                    vm.autoSignOutCheck.value = false
                                })
                            }
                        }
            }
            if (!(isChecked xor vm.evidence.value!!.isAutoSignOutActive)) return@observe

            if (isChecked) {
                //Check GeoLimitation
                if (GeoLocationUtils.didReachGeofenceLimit()) {
                    GeoLocationUtils.showOverGeofenceLimitDialog(requireActivity() as AppCompatActivity)
                    return@observe
                }
                //Check permission
                if (GeoLocationUtils.checkGeofencePreConditions(requireContext()) != LocationPrecondition.PASSED) {
                    GeoLocationUtils.requestForGeofencePreConditions(requireActivity() as BaseActivity, true) {
                        if (it != LocationPrecondition.PASSED) {
                            vm.autoSignOutCheck.value = !isChecked
                            val text = when (it) {
                                LocationPrecondition.DENIED -> "Cannot set Location-based Sign-out due to location permission was denied."
                                LocationPrecondition.LOCATION_SETTING -> "Cannot set Location-based Sign-out due to location service was disabled."
                                LocationPrecondition.PASSED -> ""
                            }
                            Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
                        } else {
                            requestAutoSignOut()
                        }
                    }
                    isRequestingPermission = true
                } else {
                    requestAutoSignOut()
                }
            } else {
                requestAutoSignOut()
            }

        }
    }

    private fun bindForm(evidence: VisitorEvidence) {
        CustomViewLibrary.closeProgressBar()
        hideLoadingOverlay()
        binding.evidence = evidence
        //arrive.
        if (!evidence.arrive.cusvals.isNullOrEmpty()) {
            binding.arriveCusvalLayout.removeAllViews()
            evidence.arrive.cusvals
                    .mapNotNull { CusvalView(it, requireContext(), true).getView() }
                    .forEach { binding.arriveCusvalLayout.addView(it) }
        }

        //auto signout
//        binding.autoSignoutSwitch.visibility = if(evidence.site.getGeofenceData() != null) View.VISIBLE else View.GONE
        binding.autoSignoutSwitch.visibility = View.GONE

        //leave
        if (evidence.leave?.cusvals == null) {
            binding.leaveCusvalSection.visibility = View.GONE
            binding.leaveBtnLayout.visibility = View.VISIBLE
        } else {
            binding.leaveCusvalSection.visibility = View.VISIBLE
            binding.leaveBtnLayout.visibility = View.GONE
            binding.leaveCusvalLayout.removeAllViews()
            evidence.leave.cusvals
                    .mapNotNull { CusvalView(it, requireContext(), true).getView() }
                    .forEach { binding.leaveCusvalLayout.addView(it) }
        }
    }

    fun submitOnClicked(v: View) {
        findNavController().popBackStack()
    }

    fun leaveBtnOnClicked(v: View) {
        //Start SignOut Form here.
        CustomViewLibrary.startProgressBar(v.rootView)
        vm.cd.add(vm.fetchVisitorEvidence(vm.evidence.value!!)
                .flatMap { evidence ->
                    if (evidence.leave != null) {
                        //this means this record is already signed out.
                        return@flatMap Single.error(ErrorEnvThrowable("This visit record is already finished the ${vm.evidence.value!!.site.tier.VISIT_TERMS.leave} process."))
                    }
                    vm.fetchVisitorSite(evidence.token.toString()).map { Pair(evidence, it) }
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ pair: Pair<VisitorEvidence, VisitorSite> ->
                    CustomViewLibrary.closeProgressBar()
                    VisitorWizardActivity.startSignOutFlow(requireActivity(), pair.second, pair.first)
                }, { throwable ->
                    throwable.printStackTrace()
                    CustomViewLibrary.closeProgressBar()
                    DialogHelper.showAlert(requireContext(), "Error", "Something's gone wrong. Cannot perform Sign out. \n ${throwable.message}")
                    //reload the form.
                    loadEvidence()
                }))


        //VisitorWizardActivity.startSignOutFlow(requireActivity(), vm.evidence!!.site, vm.evidence!!)

    }

    fun locationOnClicked(v: View) {
        vm.evidence.value?.site?.getGeofenceData()?.let {
            GeofencingFragment.newInstance(it, true).apply {
                setTitle("Site Location")
            }.show(requireActivity().supportFragmentManager, null)
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if(isRequestingPermission) {
            (activity as? VisitorMainActivity)?.registerToSaveInstanceState = Pair(TAG, Bundle().apply { putString("EVIDENCE_ID", args.evidenceId) })
        }
    }

    private fun hideLoadingOverlay() {
        binding.loadingOverlay.animate()
                .alpha(0f)
                .setInterpolator(AccelerateInterpolator())
                .setDuration(300)
                .withEndAction { binding.loadingOverlay.visibility = View.GONE }
                .start()
    }

    private fun showLoadingOverlay() {
        binding.loadingOverlay.apply {
            visibility = View.VISIBLE
            alpha = 0f
        }
        binding.loadingOverlay.animate()
                .alpha(1f)
                .setInterpolator(AccelerateInterpolator())
                .setDuration(300)
                .start()
    }

}