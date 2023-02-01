package au.com.safetychampion.scmobile.visitorModule.view

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import au.com.safetychampion.scmobile.R
import au.com.safetychampion.scmobile.VisitorNavGraphDirections
import au.com.safetychampion.scmobile.modules.global.GlobalData
import au.com.safetychampion.scmobile.ui.activities.AuthActivity
import au.com.safetychampion.scmobile.ui.activities.BaseActivity
import au.com.safetychampion.scmobile.ui.activities.ILoginActivity
import au.com.safetychampion.scmobile.ui.activities.LauncherActivity
import au.com.safetychampion.scmobile.utils.SharedPrefCollection
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import kotlinx.android.synthetic.main.visitor_main_activity.*

class VisitorMainActivity : BaseActivity() {

    val currentNavigationFragment: Fragment?
        get() = supportFragmentManager.findFragmentById(R.id.container)
                ?.childFragmentManager
                ?.fragments
                ?.first()
    private var permissionSaveInstanceState: Bundle? = null

    lateinit var menuPopup: VisitorMenuPopup

    private val onMenuItemClickedListener: (VisitorMenuPopup.Menu, View) -> Unit = { menu, view ->
        when (menu) {
            VisitorMenuPopup.Menu.EDIT_PROFILE -> goToProfileFragment()
            VisitorMenuPopup.Menu.LOGOUT -> actionLogout()
            else -> {
            }
        }
        menuPopup.dismiss()
    }

    private val onSwitchChangedListener: (VisitorMenuPopup.Menu, Boolean) -> Unit = { menu, isChecked ->
        when (menu) {
            VisitorMenuPopup.Menu.AUTO_SIGNOUT_FLAG -> SharedPrefCollection.geofenceAutoSignOutFlag = isChecked
            else -> { }
        }
    }

    var registerToSaveInstanceState: Pair<String, Bundle>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setupTransition()
        super.onCreate(null)
        setContentView(R.layout.visitor_main_activity)
        initToolbar()
        initMenuPopup()
        initSwitchDashboard()
        SharedPrefCollection.visitorDashboardActive = true
        if (savedInstanceState != null) {
            permissionSaveInstanceState = savedInstanceState
        }
    }

    override fun onResume() {
        super.onResume()
        permissionSaveInstanceState?.let { savedInstanceState ->
            val fragmentString = savedInstanceState.getString("FRAGMENT")
            val bundle = savedInstanceState.getBundle("BUNDLE")
            when(fragmentString) {
                GeoLeaveDialogFragment.TAG -> {
                    val evidenceString: String? = bundle?.getString("CURRENT_EVIDENCE")
                    evidenceString?.let { currentNavigationFragment?.findNavController()?.navigate(VisitorNavGraphDirections.actionGlobalAutoSignOutDialogFragment2(it)) }
                }
                VisitorEvidenceDetailFragment.TAG -> {
                    val evidenceId = bundle?.getString("EVIDENCE_ID")
                    evidenceId?.let { currentNavigationFragment?.findNavController()?.navigate(VisitorDashboardFragmentDirections.actionVisitorDashboardFragmentToVisitorEvidenceDetailFragment(it)) }
                }
                else -> return@let
            }
            permissionSaveInstanceState = null
        }
    }


    private fun setupTransition() {
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
        setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())
        window.sharedElementsUseOverlay = false
    }

    private fun initToolbar() {
        setSupportActionBar(visitor_main_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.title = ""
    }

    private fun initMenuPopup() {
        menuPopup = VisitorMenuPopup(this)
        menuPopup.contentView.findViewById<TextView>(R.id.action_logout).text = if(SharedPrefCollection.getLoginCredential(this)?.token != null) "Back to Home" else "Exit Site Attendance"
        menuPopup.onItemClickedListener = this.onMenuItemClickedListener
        menuPopup.onSwitchChanged = this.onSwitchChangedListener
    }

    private fun initSwitchDashboard() {
        if (GlobalData.isMainActivityActive) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.visitor_profile -> {
                val view = visitor_main_toolbar.findViewById<View>(R.id.visitor_profile)
                menuPopup.apply {
                    setAutoSignoutFlagSwitch(SharedPrefCollection.geofenceAutoSignOutFlag)
                    showAsDropDown(view)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun goToProfileFragment() {
        currentNavigationFragment?.apply {
            exitTransition = MaterialElevationScale(false).apply {
                duration = 300
            }
            reenterTransition = MaterialElevationScale(true).apply {
                duration = 300
            }
        }
        val nav = findNavController(R.id.container)
        val bundle = Bundle().apply { putString("task", "EDIT_PROFILE") }
        nav.navigate(R.id.action_visitorDashboardFragment_to_visitorProfileFragment, bundle)
    }

    private fun actionLogout () {
        if (GlobalData.isMainActivityActive) {
            onBackPressed()
            return
        }
        SharedPrefCollection.currentVisitorProfileId = null
        val userEnv = SharedPrefCollection.getLoginCredential(this)
        val intent = if (userEnv?.token != null) {
            Intent(this, LauncherActivity::class.java).apply { putExtra(LauncherActivity.PRESENT_TYPE, LauncherActivity.PRESENT_TYPE_NO_ANIMATION) }
        } else {
            Intent(this, AuthActivity::class.java)
        }
        startActivity(intent)
        SharedPrefCollection.visitorDashboardActive = false
        finish()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if(registerToSaveInstanceState != null) {
            outState.putString("FRAGMENT", registerToSaveInstanceState?.first)
            outState.putBundle("BUNDLE", registerToSaveInstanceState?.second)
            registerToSaveInstanceState = null
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    fun getForegroundFragment(): Fragment? {
        return currentNavigationFragment?.childFragmentManager?.fragments?.get(0)
    }
}