package au.com.safetychampion.scmobile.visitorModule.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import au.com.safetychampion.scmobile.EntryAnimation
import au.com.safetychampion.scmobile.databinding.VisitorPhoneInputFragmentBinding
import au.com.safetychampion.scmobile.modules.global.GlobalMessage
import au.com.safetychampion.scmobile.utils.CustomViewLibrary
import au.com.safetychampion.scmobile.utils.VerifierHelper
import au.com.safetychampion.scmobile.visitorModule.VisitorUtil
import au.com.safetychampion.scmobile.visitorModule.models.VisitorMessages
import au.com.safetychampion.scmobile.visitorModule.vm.VisitorSharedViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class VisitorPhoneInputFragment : Fragment(), EntryAnimation {

    companion object {
        fun newInstance() = VisitorPhoneInputFragment()
    }

    private val TAG = "VisitorPhoneInputFragment"
    private lateinit var sharedViewModel: VisitorSharedViewModel
    private lateinit var binding: VisitorPhoneInputFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = VisitorPhoneInputFragmentBinding.inflate(inflater, container, false)
        lifecycle.addObserver(binding.youtubePlayerView)
        binding.lifecycleOwner = this
        binding.main = this

        onAnimationPrepare()

        (activity as? VisitorWizardActivity)?.let {
            sharedViewModel = ViewModelProvider(it).get(VisitorSharedViewModel::class.java)
            binding.profile = sharedViewModel.profile
            binding.site = sharedViewModel.site
            binding.incProfileInput.model = sharedViewModel.profile

            initDataByRoleSelected()

        }

        onAnimationStart()
        return binding.root
    }

    private fun initDataByRoleSelected() {
        val vm = sharedViewModel

        if (vm.selectedTemplate != null && vm.selectedTemplate!!.role._id == vm.selectedRole.value!!._id) {
            bindMessage(vm.selectedTemplate!!.arrive!!.getUsableMessages())
            return
        }

        if (vm.selectedTemplate?.role?._id != vm.selectedRole.value?._id || !vm.hasLoadArriveForm) {
            if (vm.site.hasArriveForm(vm.selectedRole.value?._id ?: "")) {
                CustomViewLibrary.startProgressBar(binding.root.rootView)
                vm.cd.add(vm.fetchArriveForm()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ updatedSite ->
                            val siteFormTemp = updatedSite.forms.first { it.role._id == vm.selectedRole.value?._id }

                            vm.selectedTemplate = siteFormTemp

                            bindMessage(vm.selectedTemplate!!.arrive!!.getUsableMessages())

                            Handler(Looper.getMainLooper())
                                    .postDelayed(CustomViewLibrary::closeProgressBar, 100)
                        }, { throwable ->
                            CustomViewLibrary.closeProgressBar()
                            throwable.printStackTrace()
                            GlobalMessage.printError(throwable.message)
                            requireActivity().onBackPressed()
                        }))
            } else {
                vm.selectedTemplate = null
            }
        }
    }

    private fun bindMessage(messages: VisitorMessages) {

        messages.pre?.let {
            binding.messagesCard.visibility = View.VISIBLE
            VisitorUtil.bindMessages(it, binding.messageWebview, binding.youtubePlayerView)
        } ?: let { binding.messagesCard.visibility = View.GONE }

    }

    private fun verifyForm(): Boolean {
        val errorView = VerifierHelper.superLazyVerifier(binding.root)
        // add check for model data input
        return if (errorView == null) true else {
            errorView.requestFocus()
            false
        }
    }

    fun submitOnClicked(v: View) {
        if (!verifyForm()) return

//        Log.d("TAG", "onSubmitOnClicked: fire")
//        Navigation.findNavController(requireView()).navigate(R.id.phone_to_roles)
        //Fetch form here.
        val vm = sharedViewModel
        if (vm.selectedTemplate != null && vm.selectedTemplate!!.canDisplayArrive()) {
            Navigation.findNavController(requireView()).navigate(VisitorPhoneInputFragmentDirections.phoneToSignIn())
        } else
            Navigation.findNavController(requireView()).navigate(VisitorPhoneInputFragmentDirections.phoneToReview())
    }


    fun cancelOnClicked(v: View) {
        (activity as? VisitorWizardActivity)?.onCancelClicked()
    }

    override fun onAnimationPrepare() {
        prepareAnimateShow(binding.messagesCard)
        prepareAnimateShow(binding.profileCardView)
        prepareAnimateShow(binding.profileSubmitButton)
        prepareAnimateShow(binding.profileCancelButton)

    }

    override fun onAnimationStart() {
        var multiply = 0
        val delayConstant = 150
        fun delay(): Long = (multiply++ * delayConstant).toLong()

        animateShow(binding.messagesCard, delay())
        animateShow(binding.profileCardView, delay())
        animateShow(binding.profileSubmitButton, delay())
        animateShow(binding.profileCancelButton, delay())
    }

}