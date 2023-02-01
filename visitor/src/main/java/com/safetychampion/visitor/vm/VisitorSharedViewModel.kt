package au.com.safetychampion.scmobile.visitorModule.vm

import androidx.lifecycle.MutableLiveData
import au.com.safetychampion.scmobile.BaseViewModel
import au.com.safetychampion.scmobile.apiservice.APIResponses.ErrorEnv
import au.com.safetychampion.scmobile.exceptions.ErrorEnvThrowable
import au.com.safetychampion.scmobile.utils.GsonHelper
import au.com.safetychampion.scmobile.visitorModule.IVisitorRepository
import au.com.safetychampion.scmobile.visitorModule.VisitorRepository
import au.com.safetychampion.scmobile.visitorModule.VisitorUtil
import au.com.safetychampion.scmobile.visitorModule.models.*
import au.com.safetychampion.scmobile.visitorModule.view.VisitorWizardActivity
import io.reactivex.Single

/**
 * Created by @Minh_Khoi_MAI on 16/11/20
 */
class VisitorSharedViewModel : BaseViewModel() {
    private val repo: IVisitorRepository = VisitorRepository

    lateinit var FLOW_TYPE: String

    var token: String? = null

    lateinit var site: VisitorSite
    lateinit var profile: VisitorProfile

    var selectedTemplate: VisitorFormTemplate? = null
    val selectedRole = MutableLiveData<VisitorRole?>(null)

    var hasLoadArriveForm: Boolean = false

    //region INITIALISATION
    fun initSignInData(siteString: String?, token: String?) {
        initProfile()
        FLOW_TYPE = VisitorWizardActivity.FLOW_TYPE_SIGNIN
        site = siteString?.let { GsonHelper.getGson().fromJson(it, VisitorSite::class.java) }
                ?: return

        this.token = token
    }

    private fun initProfile() {
        cd.add(repo.getProfile(VisitorMockData.mockProfileId).subscribe ({ profile ->
            this.profile = profile
        }, { throwable ->
            this.profile = VisitorProfile()
        }))
    }

    //endregion

    /**
     * submit form when user finished the whole step.
     */
    fun submitArriveForm(): Single<VisitorEvidence> {
        //Required: Verified Profile.
        //          Verified Form.

        //repo call submit form... then return a callback
        //Assigning visitorProfile
        if (token == null || selectedTemplate == null)
            return Single.error(ErrorEnvThrowable(ErrorEnv("NO_DATA", "No sufficient data available to perform submitForm")))
        profile._id = VisitorMockData.mockProfileId
        selectedTemplate?.arrive?.form?.selectedRole = this.selectedRole.value
        return repo.performArrive(token!!, profile, selectedTemplate!!.arrive!!.form, site)
    }


    @JvmOverloads
    fun submitCode(code: String, pin: String? = null): Single<VisitorToken> { //Any can be String(token) or object which pin number
        //URL Structure: https://dev.safetychampion.tech/visitor/5fcfff86c050fa335b9b6c38/signin/5efbeba1c6bac31619e11bd8
        val pair = VisitorUtil.decodeQR(code)
                ?: return Single.error(ErrorEnvThrowable("Invalid Token"))
        return repo.getToken(pair.first, pair.second, pin)
    }

    fun fetchSite(token: String): Single<VisitorSite> {
        return repo.getSiteFetch(token)
    }

    fun fetchArriveForm(): Single<VisitorSite> {
        //Pre-conditions
        token ?: return Single.error(ErrorEnvThrowable("Invalid Token"))
        val role = selectedRole.value ?: return Single.error(ErrorEnvThrowable("No selected role."))
        val arriveFormId = site.getArriveFormId(role._id)
                ?: return Single.error(ErrorEnvThrowable("No Arrive Form Available."))
        // end of preconditions

        return repo.getFormFetch(token!!, arriveFormId, site)
                .flatMap { Single.just(site) }.doOnSuccess { hasLoadArriveForm = true }
    }



}