package au.com.safetychampion.scmobile.visitorModule.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import au.com.safetychampion.scmobile.databinding.VisitorPinInputFragmentBinding
import au.com.safetychampion.scmobile.exceptions.ErrorEnvThrowable
import au.com.safetychampion.scmobile.exts.handleCommonThrowables
import au.com.safetychampion.scmobile.ui.dialogs.SCAlertDialogFragment
import au.com.safetychampion.scmobile.utils.CustomViewLibrary
import au.com.safetychampion.scmobile.utils.DialogHelper
import au.com.safetychampion.scmobile.utils.TransitionUtils
import au.com.safetychampion.scmobile.visitorModule.models.VisitorSite
import au.com.safetychampion.scmobile.visitorModule.vm.VisitorSharedViewModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_qr_scanner.*

class VisitorPinInputFragment : Fragment() {

    companion object {
        fun newInstance() = VisitorPinInputFragment()
    }

    private lateinit var binding: VisitorPinInputFragmentBinding
    private var qrCodeReceived: String = ""
    private val vm: VisitorSharedViewModel by viewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        binding = VisitorPinInputFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        val pinInputActivity = (requireActivity() as? VisitorPinInputActivity)
        qrCodeReceived = pinInputActivity?.intent?.getStringExtra(VisitorPinInputActivity.QR_CODE_DATA)
                ?: let {
                    exitDialog()
                    return@let ""
                }
        binding.backBtn.setOnClickListener { requireActivity().finish() }
        binding.pinSubmitButton.setOnClickListener { submitOnClicked(it) }
        return binding.root
    }


    override fun onStart() {
        super.onStart()
        binding.pinInput.requestFocus()
    }


    fun submitOnClicked(v: View) {
        CustomViewLibrary.startProgressBar(v.rootView)
        val pinString = binding.pinInput.text.toString()
        vm.cd.add(vm.submitCode(qrCodeReceived, pinString).flatMap { res ->
            if (res.token != null) {
                val token: String = res.token
                vm.token = token
                return@flatMap vm.fetchSite(token)
            } else
                return@flatMap Single.just(res)
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ res ->
                    CustomViewLibrary.closeProgressBar()
                    if (res is VisitorSite) {
                        navigateToVisitorWizardActivity(res)
                    } else {
                        DialogHelper.showAlert(requireContext(), "Error", "Something wrong happened, please retry!", -1) { dialog, _ ->
                            dialog.dismiss()
                        }
                    }
                }, { throwable ->
                    CustomViewLibrary.closeProgressBar()
                    throwable.printStackTrace()
                    if (!throwable.handleCommonThrowables(requireActivity()) && throwable is ErrorEnvThrowable) {
                        //WRONG PIN code handled
                        SCAlertDialogFragment.newInstance("Invalid PIN", "Please re-enter a correct PIN", SCAlertDialogFragment.AlertType.Failure)
                                .show(requireActivity().supportFragmentManager, "Failure")
                        binding.pinInput.error = "Invalid PIN."
                        binding.pinInput.text = null
                    }

                }))
    }

    fun exitDialog() {
        val dialog = SCAlertDialogFragment.newInstance("Invalid QR Code", "Could not recognise the QR code. \nPlease retry again.", SCAlertDialogFragment.AlertType.Failure)
        dialog.onDismissListener = { _ -> requireActivity().finish() }
        dialog.show(requireActivity().supportFragmentManager, "Exit Dialog")
    }


    private fun navigateToVisitorWizardActivity(visitorSite: VisitorSite) {
        VisitorWizardActivity.startSignInFlow(requireActivity(), visitorSite, vm.token!!, requireActivity().intent.getStringExtra(VisitorWizardActivity.FROM_ACTIVITY))
        TransitionUtils.transition(requireActivity(), TransitionUtils.fadeOutIn())
        requireActivity().finish()
    }
}