package au.com.safetychampion.scmobile.visitorModule.view

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import au.com.safetychampion.scmobile.BaseViewModel
import au.com.safetychampion.scmobile.EntryAnimation
import au.com.safetychampion.scmobile.SCBaseFragment
import au.com.safetychampion.scmobile.apiservice.APIResponses.ErrorEnv
import au.com.safetychampion.scmobile.databinding.FragmentVisitorReviewBinding
import au.com.safetychampion.scmobile.exceptions.ErrorEnvThrowable
import au.com.safetychampion.scmobile.exceptions.NoViewModelException
import au.com.safetychampion.scmobile.geolocation.GeoLocationUtils
import au.com.safetychampion.scmobile.modules.incident.form.CusvalView
import au.com.safetychampion.scmobile.ui.activities.SuccessOverlayActivity
import au.com.safetychampion.scmobile.utils.CustomViewLibrary
import au.com.safetychampion.scmobile.utils.GsonHelper
import au.com.safetychampion.scmobile.utils.SharedPrefCollection
import au.com.safetychampion.scmobile.utils.ViewUtils
import au.com.safetychampion.scmobile.visitorModule.VisitorUtil
import au.com.safetychampion.scmobile.visitorModule.models.SCTextMessage
import au.com.safetychampion.scmobile.visitorModule.view.base.GeofencingFragment
import au.com.safetychampion.scmobile.visitorModule.vm.VisitorOutSharedVM
import au.com.safetychampion.scmobile.visitorModule.vm.VisitorSharedViewModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

abstract class VisitorReviewFragment<T : BaseViewModel> : SCBaseFragment(), EntryAnimation {
    abstract val typeVM: Class<T>
    protected lateinit var sharedViewModel: T
    protected lateinit var binding: FragmentVisitorReviewBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentVisitorReviewBinding.inflate(inflater, container, false)
        binding.main = this

        onAnimationPrepare()

        val mActivity = activity as? VisitorWizardActivity ?: throw NoViewModelException()
        sharedViewModel = ViewModelProvider(mActivity).get(typeVM)
        bindForm()

        onAnimationStart()
        return binding.root

    }

    override fun onResume() {
        super.onResume()
        binding.root.requestFocus()
        ViewUtils.hideSoftKeyboard(binding.root)
    }

    abstract fun bindForm()

    abstract fun submitOnClicked(v: View)

    abstract fun cancelOnClicked(v: View)

    protected fun showSuccessMessage(title: String, postMessages: SCTextMessage?, onComplete: () -> Unit) {
        CustomViewLibrary.closeProgressBar()

        if (postMessages != null) {
            requireActivity().supportFragmentManager.let {
                val dialogMsg = VisitorPostMessageDialogFragment()
                dialogMsg.messageTitle = title
                dialogMsg.messageType = postMessages.type
                dialogMsg.messageValue = postMessages.value
                dialogMsg.show(it, "POST_DIALOG_FRAGMENT")
                dialogMsg.onDismissListener = DialogInterface.OnDismissListener {
                    onComplete.invoke()

                }
            }
            return
        }

//        val successIntent = SuccessOverlayActivity.toIntent(requireContext(), "Success!", "You've successfully signed in to ${sharedViewModel.site.title}.")
        val successIntent = SuccessOverlayActivity.toIntent(requireContext(), "Success!", title)
        startActivityForResult(successIntent, null) {
            onComplete.invoke()
        }

    }

    protected fun showFailureMessage(throwable: Throwable, msg: String) {
        CustomViewLibrary.closeProgressBar()
        throwable.printStackTrace()
//        ViewUtils.showFailedActivity(binding.root, "Oops!", "Cannot signed in to ${sharedViewModel.site.title}, please review.")
        ViewUtils.showFailedActivity(binding.root, "Oops!", msg)
    }

    override fun onAnimationPrepare() {
        prepareAnimateShow(binding.profileCardView)
        prepareAnimateShow(binding.profileSubmitButton)
        prepareAnimateShow(binding.profileReviewCancelButton)
    }

    override fun onAnimationStart() {
        var multiply = 0
        val delayConstant = 150
        fun delay(): Long = (multiply++ * delayConstant).toLong()

        animateShow(binding.profileCardView, delay())
        animateShow(binding.profileSubmitButton, delay())
        animateShow(binding.profileReviewCancelButton, delay())
    }

    fun isArrive() = typeVM == VisitorSharedViewModel::class.java

    fun startGeofencingDialogFragment() {
        val geofencingFragment = GeofencingFragment.newInstance()
        geofencingFragment.show(requireActivity().supportFragmentManager, null)
    }
}

class VisitorInReviewFragment : VisitorReviewFragment<VisitorSharedViewModel>() {
    val args: VisitorInReviewFragmentArgs by navArgs()
    override val typeVM: Class<VisitorSharedViewModel>
        get() = VisitorSharedViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (args.fromAction == "phone_to_review") {
                    findNavController().popBackStack()
                } else
                    findNavController().navigate(VisitorInReviewFragmentDirections.reviewToForm())
            }
        })
    }

    override fun bindForm() {
        binding.site = sharedViewModel.site
        binding.role = sharedViewModel.selectedRole
        binding.profile = sharedViewModel.profile
        binding.incReview.formDetailTitle.text = "${sharedViewModel.site.tier.VISIT_TERMS.arrive} Information"
        //Cusval Review
        if (sharedViewModel.selectedTemplate == null || sharedViewModel.selectedTemplate?.arrive?.form?.cusvals?.isEmpty() == true) {
            binding.incReview.cusvalSection.visibility = View.GONE
        } else {
            binding.incReview.cusvalSection.visibility = View.VISIBLE
            binding.incReview.formDetailCusvalLayout.removeAllViews()
            sharedViewModel.selectedTemplate?.arrive?.form?.cusvals
                    ?.mapNotNull { CusvalView(it, requireContext(), true).getView() }
                    ?.forEach { binding.incReview.formDetailCusvalLayout.addView(it) }
        }
    }

    override fun submitOnClicked(v: View) {

//        val geoData = sharedViewModel.site.getGeofenceData()
//        if (geoData != null) {
//            val dialog = GeofencingFragment.newInstance(geoData)
//            dialog.onPositiveBtnClicked = {View -> performSubmitArrive() }
//            dialog.show(requireActivity().supportFragmentManager, null)
//        } else {
//            performSubmitArrive()
//        }
        performSubmitArrive()
    }

    private fun performSubmitArrive() {
        CustomViewLibrary.startProgressBar(binding.root)
        sharedViewModel.submitArriveForm()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    val successMessage = "You've successfully signed in at ${sharedViewModel.site.title }."
                    showSuccessMessage(successMessage,
                        sharedViewModel.selectedTemplate?.arrive?.getUsableMessages()?.post as? SCTextMessage
                    ) {
                        //Go to Dashboard
                        val evidence = it.apply { site = sharedViewModel.site }
                        val evidenceString = GsonHelper.getGson().toJson(evidence)
                        val dashboardIntent = Intent(requireContext(), VisitorMainActivity::class.java).apply {
                            if (sharedViewModel.site.getGeofenceData()?.autoSignOutConfig == true)
                                SharedPrefCollection.requestAutoSignOutDialog = evidenceString
                        }
                        context?.startActivity(dashboardIntent)
                        requireActivity().finish()
                        //requireActivity().finishAffinity()
                    }
                }, { throwable ->
                    showFailureMessage(throwable, "Cannot signed in to ${sharedViewModel.site.title}, please review.")
                }).let { cd.add(it) }
    }

    override fun cancelOnClicked(v: View) {
        (activity as? VisitorWizardActivity)?.onCancelClicked()
    }

}

class VisitorOutReviewFragment : VisitorReviewFragment<VisitorOutSharedVM>() {
    val args: VisitorOutReviewFragmentArgs by navArgs()
    override val typeVM: Class<VisitorOutSharedVM>
        get() = VisitorOutSharedVM::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (args.fromAction == "signout_to_review_remove_backstack") {
                    requireActivity().finish()
                } else
                   findNavController().navigate(VisitorOutReviewFragmentDirections.backToForm())
            }
        })
    }

    override fun bindForm() {
        binding.site = sharedViewModel.site
        binding.role = sharedViewModel.selectedRole
        binding.profile = sharedViewModel.currentEvidence.visitor.pii.let { VisitorUtil.piiToProfile(it) }
        binding.incReview.formDetailTitle.text = "${sharedViewModel.site.tier.VISIT_TERMS.leave} Detail"
        //Cusval Review
        if (sharedViewModel.selectedTemplate == null || (sharedViewModel.selectedTemplate?.leave?.form?.cusvals?.size
                        ?: 0) == 0) {
            binding.incReview.cusvalSection.visibility = View.GONE
        } else {
            binding.incReview.cusvalSection.visibility = View.VISIBLE
            binding.incReview.formDetailCusvalLayout.removeAllViews()
            sharedViewModel.selectedTemplate?.leave?.form?.cusvals
                    ?.mapNotNull { CusvalView(it, requireContext(), true).getView() }
                    ?.forEach { binding.incReview.formDetailCusvalLayout.addView(it) }
        }
    }

    override fun submitOnClicked(v: View) {
        CustomViewLibrary.startProgressBar(v.rootView)
        sharedViewModel.fetchVisit()
                .flatMap { evidence ->
                    return@flatMap if (evidence.leave == null) {
                        sharedViewModel.submitLeaveForm()
                    } else {
                        Single.error(ErrorEnvThrowable(ErrorEnv("already_completed", "This visit record is already finished the ${sharedViewModel.site.tier.VISIT_TERMS.leave} process.")))
                    }
                }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    //Close the current notification.
                    sharedViewModel.currentEvidence.notificationId?.let {
                        GeoLocationUtils.removeGeofence(requireContext().applicationContext, it)
                    }
                    requireActivity().intent.putExtra("FLOW_COMPLETED", true)
                    val successMessage = "You've successfully signed out from ${sharedViewModel.site.title}."
                    showSuccessMessage(successMessage,
                        sharedViewModel.selectedTemplate?.leave?.getUsableMessages()?.post as? SCTextMessage
                    ) {
                        //Go to Dashboard or close the app

                        requireActivity().finish()
                    }
                }, { throwable ->
                    throwable.printStackTrace()
                    (throwable as? ErrorEnvThrowable)?.run {
                        if (this.errorEnv!!.code.contains("already_completed")) {
                            showFailureMessage(throwable, this.errorEnv!!.printMessage())
                            sharedViewModel.currentEvidence.notificationId?.let { GeoLocationUtils.removeGeofence(requireContext().applicationContext, it) }
                            requireActivity().intent.putExtra("FLOW_COMPLETED", true)
                            requireActivity().finish()
                            return@subscribe
                        }
                    }
                    showFailureMessage(throwable, "Cannot signed out from ${sharedViewModel.site.title}, please review.")
                }).let { cd.add(it) }
    }

    override fun cancelOnClicked(v: View) {
        (activity as? VisitorWizardActivity)?.onCancelClicked()
    }

}