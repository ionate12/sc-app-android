package au.com.safetychampion.scmobile.visitorModule.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import au.com.safetychampion.scmobile.R
import au.com.safetychampion.scmobile.databinding.VisitorRecentActivitiesFragmentBinding
import au.com.safetychampion.scmobile.exceptions.ErrorEnvThrowable
import au.com.safetychampion.scmobile.ui.views.recyclerViews.SCDividerItemDecoration
import au.com.safetychampion.scmobile.utils.CustomViewLibrary
import au.com.safetychampion.scmobile.utils.DialogHelper
import au.com.safetychampion.scmobile.visitorModule.models.RecentActivityCell
import au.com.safetychampion.scmobile.visitorModule.models.VisitorEvidence
import au.com.safetychampion.scmobile.visitorModule.models.VisitorSite
import au.com.safetychampion.scmobile.visitorModule.vm.VisitorDashboardViewModel
import com.google.android.material.transition.MaterialElevationScale
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.visitor_dashboard_fragment.*

class VisitorRecentActivitiesFragment : Fragment() {

    private lateinit var binding: VisitorRecentActivitiesFragmentBinding
    private val vm: VisitorDashboardViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = VisitorRecentActivitiesFragmentBinding.inflate(inflater, container, false)
        binding.main = this
        binding.lifecycleOwner = this

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initRecentActivitiesRecView()
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
    }

    override fun onResume() {
        super.onResume()
        initToolbar()
    }

    override fun onStop() {
        super.onStop()
        deinitToolbar()
    }


    private fun initObservers() {
        vm.recentActivities.observe(viewLifecycleOwner, { list ->
            val mList = list.sortedByDescending { it.arriveDate }.map { it.toRecentActivityCell() }
            (recent_activites_rec_view.adapter as VisitorHistoryListAdapter).setDataSource(mList)
        })
    }

    private fun initRecentActivitiesRecView() {
        recent_activites_rec_view.apply {
            setHasFixedSize(true)
            recent_activites_rec_view.adapter = VisitorHistoryListAdapter().apply {
                clickHandler = { view, evidenceId ->
                    exitTransition = MaterialElevationScale(false).apply {
                        duration = 400
                    }
                    reenterTransition = MaterialElevationScale(true).apply {
                        duration = 400
                    }

                    val transitionName = getString(R.string.recent_activity_detail_transition_name)
                    val extra = FragmentNavigatorExtras(view to transitionName)
                    val direction = VisitorRecentActivitiesFragmentDirections.actionVisitorRecentActivitiesFragmentToVisitorEvidenceDetailFragment(evidenceId)
                    findNavController().navigate(direction, extra)
                }
                signOutButtonClickHandler = { recentActivity: RecentActivityCell ->
                    CustomViewLibrary.startProgressBar(binding.root.rootView)
                    vm.cd.add(vm.fetchVisitAndIncludeSiteFromDB(recentActivity._id)
                            .flatMap { evidence ->
                                if (evidence.leave != null) {
                                    return@flatMap Single.error(ErrorEnvThrowable("This visit record is already finished the ${recentActivity.visitTerm.leave ?: "Sign-out"} process."))
                                }
                                //vm.loadVisitorSite(evidence.site._id).map { Pair(evidence, it) }
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
                            }))
                }
            }
            addItemDecoration(SCDividerItemDecoration(context))
        }

    }

    private fun initToolbar() {
        (activity as VisitorMainActivity).apply {
            this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
            this.supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp)
        }
    }

    private fun deinitToolbar() {
        (activity as VisitorMainActivity).apply {
            this.supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp)
        }
    }

}