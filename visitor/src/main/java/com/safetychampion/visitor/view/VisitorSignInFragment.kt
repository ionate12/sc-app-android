package au.com.safetychampion.scmobile.visitorModule.view

import android.view.View
import androidx.navigation.fragment.findNavController
import au.com.safetychampion.scmobile.visitorModule.view.base.VisitorFormFragment
import au.com.safetychampion.scmobile.visitorModule.vm.VisitorSharedViewModel


class VisitorSignInFragment : VisitorFormFragment<VisitorSharedViewModel>() {
    override val vmType: Class<VisitorSharedViewModel>
        get() = VisitorSharedViewModel::class.java


    companion object {
        fun newInstance() = VisitorSignInFragment()
    }

    override fun initForm() {
        val vm = sharedViewModel

        bindMessage(vm.selectedTemplate!!.arrive!!.getUsableMessages())
        bindFormToUI(vm.site, vm.selectedTemplate!!.arrive!!.form)
        onAnimationStart()

    }

    private fun verifyForm(): Boolean {
        var verified = true
        sharedViewModel.selectedTemplate!!.arrive!!.form.cusvals.forEach {
            if (!it.selfVerify()) {
                verified = false
                it.triggerVerifyCallback(verified)
                it.verifyObserver.onNext(false)
            }
        }
        return verified
    }

    override fun onSubmitOnClicked(v: View) {
        if (!verifyForm()) return
        // *** verify form input if need
//        binding.contentView.requestFocus()
//        ViewUtils.hideSoftKeyboard(binding.contentView)
        findNavController().navigate(VisitorSignInFragmentDirections.signInToReview())
    }

    override fun cancelOnClicked(v: View) {
        (activity as? VisitorWizardActivity)?.onCancelClicked()
    }



}