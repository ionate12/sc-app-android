package au.com.safetychampion.scmobile.visitorModule.view

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.transition.Slide
import au.com.safetychampion.scmobile.R
import au.com.safetychampion.scmobile.databinding.VisitorProfileFragmentBinding
import au.com.safetychampion.scmobile.utils.VerifierHelper
import au.com.safetychampion.scmobile.utils.ViewUtils
import au.com.safetychampion.scmobile.visitorModule.vm.VisitorProfileViewModel
import com.google.android.material.transition.MaterialContainerTransform
import io.reactivex.disposables.CompositeDisposable

class VisitorProfileFragment : Fragment() {

    companion object {
        fun newInstance() = VisitorProfileFragment()
    }

    val viewModel: VisitorProfileViewModel by viewModels()

    private lateinit var binding: VisitorProfileFragmentBinding
    private val cd = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        if (arguments?.get("task") == "EDIT_PROFILE") {
            viewModel.isEditMode = true
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as? VisitorMainActivity)?.apply {
            this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = VisitorProfileFragmentBinding.inflate(inflater, container, false)
        binding.main = this
        binding.lifecycleOwner = this
        initObservers()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            enterTransition = MaterialContainerTransform().apply {
                startView = requireActivity().findViewById(R.id.visitor_main_toolbar)
                endView = contentView
                duration = 300
                scrimColor = Color.TRANSPARENT
                containerColor = ContextCompat.getColor(requireContext(), R.color.white_alpha_40)
                startContainerColor = ContextCompat.getColor(requireContext(), R.color.sc_header)
                endContainerColor = ContextCompat.getColor(requireContext(), R.color.white)
            }
            returnTransition = Slide().apply {
                duration = 225
                addTarget(R.id.profile_card_view)
            }
        }
        if (viewModel.isEditMode) {
            viewModel.loadProfile()
        }

    }

    private fun initObservers() {
        viewModel.model.observe(viewLifecycleOwner, {
             binding.incProfileInput.model = it
        })
    }



    private fun verifyForm(): Boolean {
        val errorView = VerifierHelper.superLazyVerifier(binding.root)
        // add check for model data input
        return if (errorView == null) true else {
            errorView.requestFocus()
            false
        }
    }

    fun onSubmitOnClicked(v: View) {
        Log.d("TAG", "onSubmitOnClicked: ")
        if (!verifyForm()) return
        viewModel.saveProfile {
            findNavController().popBackStack()
//            findNavController().run {
//                if(graph.id == R.id.visitor_nav_graph) {
//                    navigate(R.id.action_visitorProfileFragment_to_visitorDashboardFragment2)
//                }
//            }
        }
    }

    override fun onPause() {
        super.onPause()
        ViewUtils.hideSoftKeyboard(binding.root)
    }




}
