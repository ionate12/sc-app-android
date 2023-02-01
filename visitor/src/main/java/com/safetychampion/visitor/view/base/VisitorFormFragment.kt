package au.com.safetychampion.scmobile.visitorModule.view.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import au.com.safetychampion.scmobile.BaseViewModel
import au.com.safetychampion.scmobile.EntryAnimation
import au.com.safetychampion.scmobile.SCBaseFragment
import au.com.safetychampion.scmobile.databinding.VisitorSignInFragmentBinding
import au.com.safetychampion.scmobile.modules.incident.form.CusvalForm
import au.com.safetychampion.scmobile.utils.ViewUtils
import au.com.safetychampion.scmobile.visitorModule.VisitorUtil
import au.com.safetychampion.scmobile.visitorModule.models.VisitorForm
import au.com.safetychampion.scmobile.visitorModule.models.VisitorMessages
import au.com.safetychampion.scmobile.visitorModule.models.VisitorSite
import au.com.safetychampion.scmobile.visitorModule.view.VisitorWizardActivity
import au.com.safetychampion.scmobile.visitorModule.vm.VisitorSharedViewModel

/**
 * Created by @Minh_Khoi_MAI on 8/01/21
 * This is a base fragment for SignIn and SignOut Fragments
 */
abstract class VisitorFormFragment<T : BaseViewModel> : SCBaseFragment(), EntryAnimation {
    abstract val vmType: Class<T>
    protected lateinit var sharedViewModel: T
    protected lateinit var binding: VisitorSignInFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = VisitorSignInFragmentBinding.inflate(inflater, container, false)
        onAnimationPrepare()
        if (requireActivity() is VisitorWizardActivity) {
            sharedViewModel = ViewModelProvider(requireActivity()).get(vmType)
        } else {
            throw Exception()
        }
        initForm()
        lifecycle.addObserver(binding.youtubePlayerView)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestFocus()
        ViewUtils.hideSoftKeyboard(binding.root)
    }


    protected fun bindFormToUI(site: VisitorSite, form: VisitorForm) {
        binding.main = this
        binding.site = site
        binding.cusvalLayout.removeAllViews()
        if (form.cusvals.isEmpty()) {
            binding.formCard.visibility = View.GONE
            return
        }
        for(i in form.cusvals.indices) {
            val cusval = form.cusvals[i]
            binding.cusvalLayout.addView(CusvalForm.initCusvalForm(cusval, requireContext(), cd).apply { id =  i })
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.cusvalLayout.removeAllViews()
    }

    protected fun bindMessage(messages: VisitorMessages) {

        messages._in?.let {
            binding.messagesCard.visibility = View.VISIBLE
            VisitorUtil.bindMessages(it, binding.messageWebview, binding.youtubePlayerView)
        } ?: let { binding.messagesCard.visibility = View.GONE }

    }


    override fun onAnimationPrepare() {
        prepareAnimateShow(binding.messagesCard)
        prepareAnimateShow(binding.formCard)
        prepareAnimateShow(binding.nextBtn)
        prepareAnimateShow(binding.cancelBtn)
    }

    override fun onAnimationStart() {
        var multiply = 0
        val delayConstant = 150
        fun delay(): Long = (multiply++ * delayConstant).toLong()

        animateShow(binding.messagesCard, delay())
        animateShow(binding.formCard, delay())
        animateShow(binding.nextBtn, delay())
        animateShow(binding.cancelBtn, delay())
    }

    fun isArrive(): Boolean = vmType == VisitorSharedViewModel::class.java


    protected abstract fun initForm()
    abstract fun onSubmitOnClicked(v: View)
    abstract fun cancelOnClicked(v: View)

}