package au.com.safetychampion.scmobile.visitorModule.view

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionManager
import au.com.safetychampion.scmobile.BuildConfig
import au.com.safetychampion.scmobile.R
import au.com.safetychampion.scmobile.SCBaseFragment
import au.com.safetychampion.scmobile.constantValues.Constants.MD_MOTION_DURATION
import au.com.safetychampion.scmobile.databinding.VisitorDashboardFragmentBinding
import au.com.safetychampion.scmobile.exceptions.ErrorEnvThrowable
import au.com.safetychampion.scmobile.geolocation.GeoLocationUtils
import au.com.safetychampion.scmobile.geolocation.LocationPrecondition
import au.com.safetychampion.scmobile.modules.global.GlobalData
import au.com.safetychampion.scmobile.modules.scanner.QRCodeScannerActivity
import au.com.safetychampion.scmobile.ui.activities.BaseActivity
import au.com.safetychampion.scmobile.ui.views.recyclerViews.SCDividerItemDecoration
import au.com.safetychampion.scmobile.utils.CustomViewLibrary
import au.com.safetychampion.scmobile.utils.DialogHelper
import au.com.safetychampion.scmobile.utils.GsonHelper
import au.com.safetychampion.scmobile.utils.SharedPrefCollection
import au.com.safetychampion.scmobile.visitorModule.models.RecentActivityCell
import au.com.safetychampion.scmobile.visitorModule.models.VisitorEvidence
import au.com.safetychampion.scmobile.visitorModule.models.VisitorSite
import au.com.safetychampion.scmobile.visitorModule.vm.AutoSignOutVM
import au.com.safetychampion.scmobile.visitorModule.vm.VisitorDashboardViewModel
import com.google.android.material.transition.MaterialArcMotion
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialElevationScale
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_geofencing.*
import kotlinx.android.synthetic.main.visitor_dashboard_fragment.*
import java.util.*

class VisitorDashboardFragment : SCBaseFragment() {

    private val vm: VisitorDashboardViewModel by viewModels()
    private val autoSignoutVM: AutoSignOutVM by viewModels()
    private val geofenceRequestHandler by lazy { Handler(Looper.getMainLooper()) }
    private lateinit var binding: VisitorDashboardFragmentBinding
    private var geofenceRequestCompleted = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onResume() {
        super.onResume()
        (activity as? VisitorMainActivity)?.apply {
            this.supportActionBar?.setDisplayHomeAsUpEnabled(GlobalData.isMainActivityActive)
        }
        cd.add(vm.fetchVisits(vm.profileId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    //Toast.makeText(requireContext(), "Fetch Visits Completed!", Toast.LENGTH_SHORT).show()
                }, { throwable ->
                    throwable.printStackTrace()
                    Toast.makeText(requireContext(), "Fetch Visits Error!", Toast.LENGTH_SHORT).show()
                }))
        location_permission_layout.visibility = View.GONE
//        val locationPermission = GeoLocationUtils.checkGeofencePreConditions(requireContext())
//        if(locationPermission != LocationPrecondition.PASSED) {
//            location_permission_layout.visibility = View.VISIBLE
//            open_settings_btn.text = if(locationPermission == LocationPrecondition.LOCATION_SETTING) "Turn On\nLocation" else "Change\nPermission"
//        } else {
//            location_permission_layout.visibility = View.GONE
//        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = VisitorDashboardFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.main = this
        binding.seeAllButton.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        CustomViewLibrary.startProgressBar(binding.root)
        Handler(Looper.getMainLooper()).postDelayed({
            CustomViewLibrary.closeProgressBar()
        }, 2000)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val didHandle = handleAutoSignout()
//        if (!didHandle && !geofenceRequestCompleted) {
//            autoSignoutVM.triggerGeofenceRequests = true
//        }
        initRecentActivitiesRecView()
        initObservers()
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.visitor_toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun initObservers() {
        vm.profile.observe(viewLifecycleOwner, { profile ->
            binding.profile = profile
        })

        vm.recentActivities.observe(viewLifecycleOwner, { list ->
            val mList = list.sortedByDescending { it.arriveDate }.map { it.toRecentActivityCell() }
            (recent_activites_rec_view.adapter as VisitorHistoryListAdapter).setDataSource(mList)
            val currentSigninList = list.filter { it.isActive }.map { it.toRecentActivityCell() }
            setCurrentSignIn(currentSigninList)
            triggerAutoSignoutRequest()
            CustomViewLibrary.closeProgressBar()
        })
    }

    private fun setCurrentSignIn(currentSignInList: List<RecentActivityCell>) {
        if (currentSignInList.isNotEmpty()) {
            binding.containerCurrentSignin.removeAllViews()
            currentSignInList.sortedWith(compareByDescending<RecentActivityCell> { it.arrive.date }.thenByDescending { it.arrive.time })
                    .take(1)
                    .map { VisitorCurrentSignInView.newInstance(requireContext(), "${it.location} - ${it.subLocation}", it.arrive.dateTime()) }
                    .forEach { binding.containerCurrentSignin.addView(it) }
            setSignOutDashboardVisibility(true)
            setSignInDashboardVisibility(false)
        } else {
            setSignOutDashboardVisibility(false)
            setSignInDashboardVisibility(true)
        }
    }

    private fun initRecentActivitiesRecView() {
        recent_activites_rec_view.apply {
            setHasFixedSize(true)
            recent_activites_rec_view.adapter = VisitorHistoryListAdapter().apply {
                clickHandler = { view, evidenceId ->

                    exitTransition = MaterialElevationScale(false).apply {
                        duration = MD_MOTION_DURATION
                    }
                    reenterTransition = MaterialElevationScale(true).apply {
                        duration = MD_MOTION_DURATION

                    }

                    val transitionName = getString(R.string.recent_activity_detail_transition_name)
                    val extra = FragmentNavigatorExtras(view to transitionName)
                    val direction = VisitorDashboardFragmentDirections.actionVisitorDashboardFragmentToVisitorEvidenceDetailFragment(evidenceId)
                    findNavController().navigate(direction, extra)
                }
                signOutButtonClickHandler = signOutButtonOnClicked()
            }
            addItemDecoration(SCDividerItemDecoration(context))
        }

    }

    private fun setSignOutDashboardVisibility(visible: Boolean) {
        sign_out_layout.visibility = if (visible) View.VISIBLE else View.GONE
        sign_in_another_site_btn.visibility = if (visible) View.VISIBLE else View.GONE
    }

    private fun setSignInDashboardVisibility(visible: Boolean) {
        val status = if (visible) View.VISIBLE else View.GONE
        sign_in_label.visibility = status
        sign_in_layout.visibility = status
    }


    //region ON CLICK ACTIONS
    fun qrCodeOnClicked(v: View) {
        val options = ActivityOptions.makeSceneTransitionAnimation(
                activity,
                v,
                "qr_scanner_shared_element_container" // The transition name to be matched in Activity B.
        )
        val intent = Intent(activity, QRCodeScannerActivity::class.java)
        startActivity(intent, options.toBundle())
    }

    fun visitedSitesOnClicked(v: View) {

    }

    fun signOutBtnOnClicked(v: View) {
        CustomViewLibrary.startProgressBar(v.rootView)
        val recentActivity = vm.recentActivities.value?.filter { it.isActive }?.sortedBy { it.arriveDate }?.firstOrNull()
                ?: return
        val evidence = recentActivity.toVisitorEvidence()
        val mToken = evidence.token ?: throw Exception("There is no token to Sign out.")
        vm.cd.add(vm.loadVisitorSite(recentActivity.siteId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { site ->
                    CustomViewLibrary.closeProgressBar()
                    VisitorWizardActivity.startSignOutFlow(requireActivity(), site, evidence)
                })
    }

    fun notYouBtnOnClicked(v: View) {

    }

    fun signInAnotherSiteOnClicked(v: View) {
        val transform = MaterialContainerTransform().apply {
            startView = v
            endView = sign_in_layout

            addTarget(endView as LinearLayout)

            setPathMotion(MaterialArcMotion())

            scrimColor = Color.TRANSPARENT
        }

        TransitionManager.beginDelayedTransition(visitor_dashboard, transform)
        v.visibility = View.GONE
        sign_in_label.visibility = View.VISIBLE
        sign_in_layout.visibility = View.VISIBLE
    }

    fun seeAllClick(v: View) {
        //val bundle = bulderOf("","acd")
        findNavController().navigate(R.id.action_dashboard_to_recent_activities)
    }

    fun signOutButtonOnClicked(): (RecentActivityCell) -> Unit {
        return { recentActivity: RecentActivityCell ->
            CustomViewLibrary.startProgressBar(binding.root.rootView)
            vm.cd.add(vm.fetchVisitAndIncludeSiteFromDB(recentActivity._id)
                    .flatMap { evidence ->
                        if (evidence.leave != null) {
                            return@flatMap Single.error(ErrorEnvThrowable("This visit record is already finished the ${evidence.site.tier.VISIT_TERMS.leave} process."))
                        }
                        // vm.loadVisitorSite(evidence.site._id).map { Pair(evidence, it) }
                        vm.fetchVisitorSite(evidence.token.toString()).map { Pair(evidence, it) }
                    }.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ pair: Pair<VisitorEvidence, VisitorSite> ->
                        CustomViewLibrary.closeProgressBar()
                        VisitorWizardActivity.startSignOutFlow(requireActivity(), pair.second, pair.first)
                    }, { throwable ->
                        throwable.printStackTrace()
                        CustomViewLibrary.closeProgressBar()
                        DialogHelper.showAlert(requireContext(), "Error", "Something's gone wrong. Cannot perform Sign out. \n ${throwable.message}")
                    }))
        }
    }

    fun locationSettingsOnClicked(v: View) {
        (requireActivity() as? BaseActivity)?.let {
            GeoLocationUtils.requestForGeofencePreConditions(it) {
                onResume()
            }
        }

    }

    //endregion

    //region AUTO-SIGNOUT

    private fun handleAutoSignout(): Boolean {
//        val newEvidenceData = VisitorMockData.evidenceStringData
        val newEvidenceData = SharedPrefCollection.requestAutoSignOutDialog
        if(newEvidenceData != null) {
            SharedPrefCollection.requestAutoSignOutDialog = null
            val evidence: VisitorEvidence = GsonHelper.getGson().fromJson(newEvidenceData, VisitorEvidence::class.java)
            if (SharedPrefCollection.geofenceAutoSignOutFlag) {
                cd.add(vm.setAutoSignout(evidence._id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            autoSignoutVM.triggerGeofenceRequests = true
                            triggerAutoSignoutRequest()
                        }, { throwable -> throwable.printStackTrace() }))
            } else {
                val autoSignOutFragment = GeoLeaveDialogFragment.newInstance(evidence).apply {
                    onPositiveBtnClicked = {
                        autoSignoutVM.triggerGeofenceRequests = true
                        triggerAutoSignoutRequest()
                    }
                }
                autoSignOutFragment.show(requireActivity().supportFragmentManager, "AutoSignOutFragment")
            }
        }

        return newEvidenceData != null
    }



    private fun triggerAutoSignoutRequest() {
        geofenceRequestHandler.removeCallbacksAndMessages(null)
        geofenceRequestHandler.postDelayed({
            activity?.runOnUiThread {
                autoSignoutVM.requestAutoSignOut(requireContext(), vm.recentActivities.value!!.map { it.toVisitorEvidence() }, {
                    Toast.makeText(requireContext(), "Geofences has been Added", Toast.LENGTH_SHORT).show()
                    geofenceRequestCompleted = true
                }, { errorCode ->
                    if (errorCode == "PERMISSION_REQUIRED" && BuildConfig.DEBUG) {
                        Toast.makeText(requireContext(), "[DEBUG]Cannot set Geofences, Location Permission is required", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }, 3000)
    }

}