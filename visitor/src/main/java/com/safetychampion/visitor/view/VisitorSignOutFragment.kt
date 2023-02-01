package au.com.safetychampion.scmobile.visitorModule.view

import android.view.View
import androidx.navigation.fragment.findNavController
import au.com.safetychampion.scmobile.utils.CustomViewLibrary
import au.com.safetychampion.scmobile.utils.DialogHelper
import au.com.safetychampion.scmobile.utils.ViewUtils
import au.com.safetychampion.scmobile.visitorModule.models.VisitorFormTemplate
import au.com.safetychampion.scmobile.visitorModule.view.base.VisitorFormFragment
import au.com.safetychampion.scmobile.visitorModule.vm.VisitorOutSharedVM
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by @Minh_Khoi_MAI on 8/01/21
 */
class VisitorSignOutFragment : VisitorFormFragment<VisitorOutSharedVM>() {
    override val vmType: Class<VisitorOutSharedVM>
        get() = VisitorOutSharedVM::class.java

    override fun initForm() {
        val vm = sharedViewModel
        if (vm.hasLoadLeaveForm) {
            if (vm.selectedTemplate != null && vm.selectedTemplate!!.role._id == vm.selectedRole.value!!._id) {
                loadUI(vm.selectedTemplate!!)
            } else {
                findNavController().navigate(VisitorSignOutFragmentDirections.signoutToReviewRemoveBackstack())
            }
        } else if (vm.site.hasLeaveForm(vm.selectedRole.value?._id ?: "")) {
            CustomViewLibrary.startProgressBar(binding.root.rootView)
            cd.add(vm.fetchLeaveForm()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ site ->
                        CustomViewLibrary.closeProgressBar()
                        vm.site = site
                        vm.selectedTemplate = site.forms.find { it.role._id == vm.selectedRole.value?._id }
                        if (vm.selectedTemplate == null || !vm.selectedTemplate!!.canDisplayLeave()) {
                            findNavController().navigate(VisitorSignOutFragmentDirections.signoutToReviewRemoveBackstack())
                        } else
                            loadUI(vm.selectedTemplate!!)
                    }, { throwable ->
                        DialogHelper.showAlert(requireContext(), "Something wrong!!!", throwable.message, -1) { dialog, which ->
                            dialog.dismiss()
                            requireActivity().finish()
                        }
                    }))

        } else {
            findNavController().navigate(VisitorSignOutFragmentDirections.signoutToReviewRemoveBackstack())
        }
    }


    private fun loadUI(selectedTemplate: VisitorFormTemplate) {
        bindMessage(selectedTemplate.leave!!.getUsableMessages())
        bindFormToUI(sharedViewModel.site, selectedTemplate.leave.form)
        onAnimationStart()
    }

    override fun onSubmitOnClicked(v: View) {
        var verified = true
        binding.contentView.requestFocus()
        ViewUtils.hideSoftKeyboard(binding.contentView)
        sharedViewModel.selectedTemplate!!.leave!!.form.cusvals.forEach {
            if (!it.selfVerify()) {
                verified = false
                it.triggerVerifyCallback(verified)
                it.verifyObserver.onNext(false)
            }
        }
        if (verified) {

            findNavController().navigate(VisitorSignOutFragmentDirections.signoutToReview())
        }
    }

    override fun cancelOnClicked(v: View) {
        (activity as? VisitorWizardActivity)?.onCancelClicked()
    }
}