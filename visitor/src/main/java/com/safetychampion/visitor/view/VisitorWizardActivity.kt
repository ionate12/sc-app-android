package au.com.safetychampion.scmobile.visitorModule.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import au.com.safetychampion.scmobile.BaseViewModel
import au.com.safetychampion.scmobile.R
import au.com.safetychampion.scmobile.databinding.ActivityVisitorWizardBinding
import au.com.safetychampion.scmobile.listeners.GeneralCallback
import au.com.safetychampion.scmobile.ui.activities.AuthActivity
import au.com.safetychampion.scmobile.ui.activities.BaseActivity
import au.com.safetychampion.scmobile.ui.activities.ILoginActivity
import au.com.safetychampion.scmobile.ui.dialogs.ExitWarningDialog
import au.com.safetychampion.scmobile.utils.DialogHelper
import au.com.safetychampion.scmobile.utils.GsonHelper
import au.com.safetychampion.scmobile.utils.TransitionUtils
import au.com.safetychampion.scmobile.visitorModule.models.VisitorEvidence
import au.com.safetychampion.scmobile.visitorModule.models.VisitorSite
import au.com.safetychampion.scmobile.visitorModule.vm.VisitorOutSharedVM
import au.com.safetychampion.scmobile.visitorModule.vm.VisitorSharedViewModel

/**
 * Created by @Minh_Khoi_MAI on 23/9/20
 */
class VisitorWizardActivity : BaseActivity() {
    companion object {

        const val SITE_DATA_KEY = "VISITOR_SITE"
        const val TOKEN = "TOKEN"
        const val EVIDENCE_ID = "evidence_id"
        const val FLOW_TYPE = "FLOW_TYPE"
        const val FLOW_TYPE_SIGNOUT = "FLOW_SIGN_OUT"
        const val FLOW_TYPE_SIGNIN = "FLOW_SIGN_IN"
        const val FROM_ACTIVITY = "FROM_ACTIVITY"

        //This request from pendingIntent in notification
        const val PENDING_SIGNOUT_REQUEST = "PENDING_SIGNOUT_REQUEST"

        @JvmStatic
        @JvmOverloads
        fun startSignInFlow(activity: Activity, site: VisitorSite, token: String, fromActivity: String? = null) {
            val siteString = GsonHelper.getGson().toJson(site)
            val intent = Intent(activity, VisitorWizardActivity::class.java).apply {
                putExtra(SITE_DATA_KEY, siteString)
                putExtra(TOKEN, token)
                putExtra(FLOW_TYPE, FLOW_TYPE_SIGNIN)
                putExtra(FROM_ACTIVITY, fromActivity ?: activity.javaClass.simpleName)
            }
            activity.startActivity(intent)
        }

        @JvmStatic
        fun startSignOutFlow(activity: Activity, site: VisitorSite, evidence: VisitorEvidence) {
            val siteString = GsonHelper.getGson().toJson(site)
            val intent = Intent(activity, VisitorWizardActivity::class.java).apply {
                putExtra(SITE_DATA_KEY, siteString)
                putExtra(EVIDENCE_ID, evidence._id)
                putExtra(FLOW_TYPE, FLOW_TYPE_SIGNOUT)
            }
            activity.startActivity(intent)
        }

    }

    lateinit var binding: ActivityVisitorWizardBinding
    lateinit var viewModel: BaseViewModel
    lateinit var flow: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(intent.flags == Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY) {
            gotoVisitDashboard()
            finish()
            return
        }
        flow = intent.getStringExtra(FLOW_TYPE) ?: throw Exception("No Flow Type declared here")
        initVM()
        initNavHost()
        initData()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_visitor_wizard)
        initToolbar()
    }

    private fun initVM() {
        viewModel = if (flow == FLOW_TYPE_SIGNIN) {
            ViewModelProvider(this).get(VisitorSharedViewModel::class.java)
        } else {
            ViewModelProvider(this).get(VisitorOutSharedVM::class.java)
        }
    }

    private fun initNavHost() {
        val navHost = NavHostFragment.create(if (flow == FLOW_TYPE_SIGNIN) R.navigation.visitor_wizard_nav_graph else R.navigation.visitor_sign_out_nav_graph)
        supportFragmentManager.beginTransaction()
                .replace(R.id.visitor_wizard_nav_host, navHost)
                .setPrimaryNavigationFragment(navHost)
                .commit()
    }

    private fun initData() {
        val flow = intent.getStringExtra(FLOW_TYPE) ?: throw Exception("No Flow Type declared here")
        val siteString = intent.getStringExtra(SITE_DATA_KEY)
        val token = intent.getStringExtra(TOKEN)
        if (flow == FLOW_TYPE_SIGNOUT) {
            val evidenceId = intent.getStringExtra(EVIDENCE_ID) ?: run {
                DialogHelper.showAlert(this, null, "Cannot perform Sign Out")
                return
            }
            (viewModel as? VisitorOutSharedVM)?.initSignOutData(evidenceId, siteString)
        } else
            (viewModel as? VisitorSharedViewModel)?.initSignInData(siteString, token)
    }


    private fun initToolbar() {
        setSupportActionBar(binding.visitorWizardToolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            //it.setDisplayShowHomeEnabled(true)
            it.title = ""
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    fun onCancelClicked(){

        val dialog = ExitWarningDialog(this, ExitWarningDialog.STYLE_EXIT, false, object : GeneralCallback {
            override fun <T> onCompleted(model: T) {
                if (!(model as Boolean)) {
                    if (intent.getStringExtra(FROM_ACTIVITY) == AuthActivity::class.java.simpleName) {
                        gotoLoginActivity()
                    }
                    finish()
                }
            }
        })

        dialog.show()
    }

    private fun gotoLoginActivity() {
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
        TransitionUtils.transition(this, TransitionUtils.fadeOutInLong());
    }

    private fun gotoVisitDashboard() {
        val intent = Intent(this, VisitorMainActivity::class.java)
        startActivity(intent)
        TransitionUtils.transition(this, TransitionUtils.fadeOutIn())
    }



}