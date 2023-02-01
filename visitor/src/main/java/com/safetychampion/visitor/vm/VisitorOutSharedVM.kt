package au.com.safetychampion.scmobile.visitorModule.vm

import androidx.lifecycle.MutableLiveData
import au.com.safetychampion.scmobile.BaseViewModel
import au.com.safetychampion.scmobile.exceptions.ErrorEnvThrowable
import au.com.safetychampion.scmobile.utils.GsonHelper
import au.com.safetychampion.scmobile.utils.RxUtils
import au.com.safetychampion.scmobile.visitorModule.IVisitorRepository
import au.com.safetychampion.scmobile.visitorModule.VisitorRepository
import au.com.safetychampion.scmobile.visitorModule.models.VisitorEvidence
import au.com.safetychampion.scmobile.visitorModule.models.VisitorFormTemplate
import au.com.safetychampion.scmobile.visitorModule.models.VisitorRole
import au.com.safetychampion.scmobile.visitorModule.models.VisitorSite
import au.com.safetychampion.scmobile.visitorModule.view.VisitorWizardActivity
import io.reactivex.Single

/**
 * Created by @Minh_Khoi_MAI on 9/01/21
 */
class VisitorOutSharedVM : BaseViewModel() {
    private val repo: IVisitorRepository = VisitorRepository
    private lateinit var FLOW_TYPE: String
    lateinit var currentEvidence: VisitorEvidence

    lateinit var site: VisitorSite

    val selectedRole = MutableLiveData<VisitorRole?>(null)

    var hasLoadLeaveForm: Boolean = false
    var selectedTemplate: VisitorFormTemplate? = null

    fun initSignOutData(evidenceId: String, siteString: String?) {
        FLOW_TYPE = VisitorWizardActivity.FLOW_TYPE_SIGNOUT
        site = siteString?.let { GsonHelper.getGson().fromJson(it, VisitorSite::class.java) }
                ?: return
        val evidence = RxUtils.blockingGet(repo.getFullEvidenceById(evidenceId)) ?: return
        currentEvidence = evidence
        selectedRole.value = evidence.visitor.role
        selectedTemplate = selectedRole.value?.let { site.getFormTemplate(it._id) }
    }


    fun fetchLeaveForm(): Single<VisitorSite> {
        val token = currentEvidence.token
        token ?: return Single.error(ErrorEnvThrowable("Invalid Token"))
        val role = selectedRole.value ?: return Single.error(ErrorEnvThrowable("No selected role."))
        val leaveFormId = site.getLeaveFormId(role._id)
                ?: return Single.error(ErrorEnvThrowable("No Leave Form Available."))
        // end of preconditions
        return repo.getFormFetch(token, leaveFormId, site)
                .flatMap { Single.just(site) }.doOnSuccess { hasLoadLeaveForm = true }
    }


    fun submitLeaveForm(): Single<VisitorEvidence> {
        return repo.performLeave(selectedTemplate?.leave?.form, currentEvidence)
    }

    fun fetchVisit(): Single<VisitorEvidence?> {
        return repo.getVisitFetch(listOf(currentEvidence)).map { list -> list.find { it._id == currentEvidence._id }}.onErrorResumeNext { Single.just(currentEvidence) }
    }


}