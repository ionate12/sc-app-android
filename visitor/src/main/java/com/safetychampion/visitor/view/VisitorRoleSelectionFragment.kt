package au.com.safetychampion.scmobile.visitorModule.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.activity.OnBackPressedCallback
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import au.com.safetychampion.scmobile.EntryAnimation
import au.com.safetychampion.scmobile.R
import au.com.safetychampion.scmobile.databinding.VisitorRoleSelectionFragmentBinding
import au.com.safetychampion.scmobile.utils.GeneralConverter
import au.com.safetychampion.scmobile.visitorModule.models.VisitorRole
import au.com.safetychampion.scmobile.visitorModule.view.base.GeofencingFragment
import au.com.safetychampion.scmobile.visitorModule.vm.VisitorSharedViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup

class VisitorRoleSelectionFragment : Fragment(), EntryAnimation, MaterialButtonToggleGroup.OnButtonCheckedListener {

    companion object {
        fun newInstance() = VisitorRoleSelectionFragment()
    }

    private lateinit var sharedViewModel: VisitorSharedViewModel
    private lateinit var binding: VisitorRoleSelectionFragmentBinding

    // show popup for back pressed event
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Handle the back button event
                (activity as? VisitorWizardActivity)?.onCancelClicked()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = VisitorRoleSelectionFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.main = this
        onAnimationPrepare()
        //If fragment has a valid view model - Do set up data for the fragment.
        if (activity is VisitorWizardActivity) {
            val mActivity = activity as VisitorWizardActivity
            sharedViewModel = ViewModelProvider(mActivity).get(VisitorSharedViewModel::class.java)
            binding.site = sharedViewModel.site
            binding.role = sharedViewModel.selectedRole
            initRoleControls(sharedViewModel.site.getRoles())
            initGeofenceDialog()
        }
        onAnimationStart()
        return binding.root
    }

    private fun presetCheckedButton(text: String) {
        binding.buttonGroup.forEach { v ->
            val btn = v as MaterialButton
            btn.isChecked = btn.text == text
        }
    }

    fun initGeofenceDialog() {
        val geoData = sharedViewModel.site.getGeofenceData()
        if (geoData != null) {
            val dialog = GeofencingFragment.newInstance(geoData)
            dialog.allowingTapOutsideToDismiss = false
            dialog.onCancelBtnClicked = { v -> (activity as? VisitorWizardActivity)?.onCancelClicked() }
            dialog.show(requireActivity().supportFragmentManager, null)
        }
    }

    override fun onButtonChecked(group: MaterialButtonToggleGroup?, checkedId: Int, isChecked: Boolean) {
        val btn: MaterialButton = group!!.findViewById(checkedId)
        if (isChecked) {
            val roles = sharedViewModel.site.getRoles()
            val selectedRole = roles.first { it._id == (btn.tag as String) }
            sharedViewModel.selectedRole.value = selectedRole
        } else {
            sharedViewModel.selectedRole.value = null
        }
    }

    @SuppressLint("ResourceType")
    fun initButtons(roles: List<VisitorRole>) {
        binding.roleSelectLayout.visibility = View.GONE
        binding.buttonGroup.visibility = View.VISIBLE
        binding.buttonGroup.run {
            this.orientation = if (roles.size > 3) LinearLayout.VERTICAL else LinearLayout.HORIZONTAL
            for (i in roles.indices) {
                this.addView(createButton(roles[i].title).apply {
                    id = i
                    tag = roles[i]._id
                })
            }
        }

        binding.buttonGroup.addOnButtonCheckedListener(this)
        sharedViewModel.selectedRole.value?.let { presetCheckedButton(it.title) }
    }

    private fun initRoleControls(roles: List<VisitorRole>) {
        initButtons(roles)
    }

    @SuppressLint("ResourceType")
    private fun createButton(text: String): MaterialButton {

        return MaterialButton(requireContext(), null, R.attr.materialButtonOutlinedStyle).apply {
            this.text = text
            insetTop = 0
            insetBottom = 0
            layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, GeneralConverter.dpToFloat(requireContext(), 50).toInt()).apply {
                weight = 1.0f
            }
            isAllCaps = false
        }
    }

    fun submitOnClicked(v: View) {
        findNavController().navigate(VisitorRoleSelectionFragmentDirections.rolesToPhone())
    }

    fun cancelOnClicked(v: View) {
        (activity as? VisitorWizardActivity)?.onCancelClicked()
    }

    override fun onAnimationPrepare() {
        prepareAnimateShow(binding.profileCardView)
        prepareAnimateShow(binding.roleCancelButton)
    }

    override fun onAnimationStart() {

        var multiply = 0
        val delayConstant = 150
        fun delay(): Long = (multiply++ * delayConstant).toLong()

        animateShow(binding.profileCardView, delay())
        animateShow(binding.roleCancelButton, delay())
    }

}