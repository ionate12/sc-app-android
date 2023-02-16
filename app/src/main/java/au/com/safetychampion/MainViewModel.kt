package au.com.safetychampion

import PrepareSignoffActionUseCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import au.com.safetychampion.data.domain.core.* // ktlint-disable no-wildcard-imports
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.TaskAssignStatusItem
import au.com.safetychampion.data.domain.models.action.network.ActionPL
import au.com.safetychampion.data.domain.models.action.network.ActionSignOff
import au.com.safetychampion.data.domain.models.auth.LoginPL
import au.com.safetychampion.data.domain.models.chemical.ChemicalSignoff
import au.com.safetychampion.data.domain.models.crisk.CriskArchivePayload
import au.com.safetychampion.data.domain.models.task.Task
import au.com.safetychampion.data.domain.usecase.action.* // ktlint-disable no-wildcard-imports
import au.com.safetychampion.data.domain.usecase.activetask.AssignTaskUseCase
import au.com.safetychampion.data.domain.usecase.activetask.GetAllActiveTaskUseCase
import au.com.safetychampion.data.domain.usecase.activetask.UnAssignTaskUseCase
import au.com.safetychampion.data.domain.usecase.assigntaskstatus.AssignManyTasksStatusItemUseCase
import au.com.safetychampion.data.domain.usecase.assigntaskstatus.AssignTaskStatusItemUseCase
import au.com.safetychampion.data.domain.usecase.auth.GetWhoAmIUseCase
import au.com.safetychampion.data.domain.usecase.auth.UserLoginUseCase
import au.com.safetychampion.data.domain.usecase.auth.UserMorphUseCase
import au.com.safetychampion.data.domain.usecase.auth.UserMultiLoginUseCase
import au.com.safetychampion.data.domain.usecase.auth.UserUnMorphUseCase
import au.com.safetychampion.data.domain.usecase.auth.UserVerifyMfaUseCase
import au.com.safetychampion.data.domain.usecase.banner.GetListBannerUseCase
import au.com.safetychampion.data.domain.usecase.chemical.* // ktlint-disable no-wildcard-imports
import au.com.safetychampion.data.domain.usecase.crisk.* // ktlint-disable no-wildcard-imports
import au.com.safetychampion.data.util.extension.koinInject
import au.com.safetychampion.data.visitor.domain.models.* // ktlint-disable no-wildcard-imports
import au.com.safetychampion.data.visitor.domain.usecase.ArriveAndUpdateUseCase
import au.com.safetychampion.data.visitor.domain.usecase.evidence.FetchEvidenceUseCase
import au.com.safetychampion.data.visitor.domain.usecase.qr.SubmitQRCodeUseCase
import au.com.safetychampion.data.visitor.domain.usecase.site.FetchSiteUseCase
import au.com.safetychampion.data.visitor.domain.usecase.site.UpdateSiteByFormFetchUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.util.*

class MainViewModel : ViewModel() {

    private val getActiveTaskUseCase: GetAllActiveTaskUseCase by koinInject()
    private val assignTaskStatusItem: AssignTaskStatusItemUseCase by koinInject()
    private val assignTasksStatusItem: AssignManyTasksStatusItemUseCase by koinInject()
    private val assignTaskUseCase: AssignTaskUseCase by koinInject()
    private val unAssignTaskUseCase: UnAssignTaskUseCase by koinInject()

    private val newActionUseCase: CreateActionUseCase by koinInject()
    private val allAction: GetListActionUseCase by koinInject()
    private val prepareSignoffActionUseCase: PrepareSignoffActionUseCase by koinInject()
    private val editActionUseCase: EditActionUseCase by koinInject()
    private val signOffActionUseCase: SignoffActionUseCase by koinInject()

    private val getListBannerUseCase: GetListBannerUseCase by koinInject()
    private val getChemicalSignoffDetailsUseCase: PerpareSignoffChemicalUseCase by koinInject()
    private val getGhsCodeUseCase: GetGhsCodeUseCase by koinInject()
    private val refreshChemicalUseCase: GetListChemicalUseCase by koinInject()
    private val signoffChemicalUseCase: SignoffChemicalUseCase by koinInject()

    private val getListCriskUseCase: GetListCriskUseCase by koinInject()
    private val getListHrLookupUseCase: GetListHrLookupItemUseCase by koinInject()
    private val getListContractorLookupUseCase: GetListContractorLookupUseCase by koinInject()
    private val getCriskSignoff: PrepareSignoffCriskUseCase by koinInject()
    private val getCriskUseCase: FetchCriskUseCase by koinInject()
    private val getCriskEvidence: GetCriskTaskEvidenceUseCase by koinInject()
    private val archiveCrisk: ArchiveCriskUseCase by koinInject()

    private val loginUseCase: UserLoginUseCase by koinInject()
    private val multiLoginUseCase: UserMultiLoginUseCase by koinInject()
    private val verifyMfaUseCase: UserVerifyMfaUseCase by koinInject()
    private val morphUseCase: UserMorphUseCase by koinInject()
    private val unmorphUseCase: UserUnMorphUseCase by koinInject()
    private val whoAmIUseCase: GetWhoAmIUseCase by koinInject()

    val _apiCallStatus = MutableSharedFlow<Pair<Int, Result<*>>>()
    val apiCallStatus = _apiCallStatus.asSharedFlow()

    suspend fun login(index: Int) {
        loginUseCase(LoginPL(email = "u3_2@minh1.co", password = "123"))
            .let { _apiCallStatus.emit(index to it) }
    }

    suspend fun multiLogin(index: Int) {
        val pl = LoginPL(email = "demomanager@safetychampion.online", password = "12345678a")
        val userId = "5efbeba4c6bac31619e11be4"
        loginUseCase(pl).doOnSucceed {
            multiLoginUseCase(userId, pl)
                .let { _apiCallStatus.emit(index to it) }
        }
    }

    suspend fun whoAmI(index: Int) {
        whoAmIUseCase().let { _apiCallStatus.emit(index to it) }
    }

    // require login as demomanager tier3
    suspend fun morph(index: Int) {
        morphUseCase("5efbeba1c6bac31619e11bd8").let {
            _apiCallStatus.emit(index to it)
        }
    }

    // require morphed
    suspend fun unmorph(index: Int) {
        unmorphUseCase().let { _apiCallStatus.emit(index to it) }
    }

    suspend fun loadActiveTasks(index: Int) {
        val result = getActiveTaskUseCase.invoke(null)
        _apiCallStatus.emit(index to result)
    }

    suspend fun loadActiveTasksReViewPlan(index: Int) {
        val result = getActiveTaskUseCase.invoke("core.module.reviewplan")
        _apiCallStatus.emit(index to result)
    }

    suspend fun assignTaskStatus(task: Task, index: Int) {
        val result = assignTaskStatusItem.invoke(task, null, null, null, null)
        _apiCallStatus.emit(index to result)
    }

    suspend fun assignTaskStatusForMany(task: List<Task>, index: Int) {
        val result = assignTasksStatusItem.invoke(task)
        _apiCallStatus.emit(index to result)
    }

    suspend fun assignTask(assignTask: TaskAssignStatusItem, ownerTask: Task, index: Int) {
        val result = assignTaskUseCase.invoke(
            task = ownerTask,
            toUserId = assignTask._id,
            moduleName = "Action",
            notes = assignTask.optionalMessage,
            dateDue = ownerTask.dateDue
        )
        _apiCallStatus.emit(index to result)
    }

    suspend fun unAssignTask(assignTask: TaskAssignStatusItem, ownerTask: Task, index: Int) {
        val result = unAssignTaskUseCase.invoke(
            task = ownerTask,
            toUserId = assignTask._id,
            moduleName = "Action",
            notes = assignTask.optionalMessage,
            dateDue = ownerTask.dateDue
        )
        _apiCallStatus.emit(index to result)
    }

    suspend fun createNewAction(payload: ActionPL, index: Int) {
        _apiCallStatus.emit(
            index to newActionUseCase.invoke(
                payload = payload
            )
        )
    }

    suspend fun getListAction(index: Int) {
        _apiCallStatus.emit(index to allAction.invoke())
    }

    suspend fun getActionSignOff(actionId: String, id: String, index: Int) {
        _apiCallStatus.emit(
            index to prepareSignoffActionUseCase.invoke(id, actionId)
        )
//
    }

    suspend fun editAction(actionPL: ActionPL, id: String, index: Int) {
        _apiCallStatus.emit(index to editActionUseCase.invoke(id, actionPL))
    }

    suspend fun signOffAction(
        actionSignOff: ActionSignOff,
        index: Int
    ) {
        _apiCallStatus.emit(
            index to signOffActionUseCase.invoke(
                actionSignOff
            )
        )
    }

    suspend fun getListBanner(index: Int) {
        _apiCallStatus.emit(index to getListBannerUseCase.invoke())
    }

    suspend fun getChemicalSignoff(id: String, moduleId: String, index: Int) {
        _apiCallStatus.emit(index to getChemicalSignoffDetailsUseCase.invoke(id, moduleId))
    }

    suspend fun refreshGHS(index: Int) {
        getGhsCodeUseCase.invoke()
        _apiCallStatus.emit(index to Result.Success("Done. Please check the logcat for more info"))
    }

    suspend fun refreshChemical(index: Int) {
        viewModelScope.launch {
            refreshChemicalUseCase.invoke()
            _apiCallStatus.emit(index to Result.Success("Done. Please check the logcat for more info"))
        }
    }

    suspend fun signoffChemical(
        signoff: ChemicalSignoff,
        index: Int
    ) {
        _apiCallStatus.emit(
            index to signoffChemicalUseCase.invoke(signoff)
        )
    }

    suspend fun getListCrisk(index: Int) {
        _apiCallStatus.emit(
            index to getListCriskUseCase.invoke()
        )
    }

    suspend fun getListHrLookup(index: Int) {
        _apiCallStatus.emit(
            index to getListHrLookupUseCase.invoke()
        )
    }
    suspend fun getListContractorLookup(index: Int) {
        _apiCallStatus.emit(
            index to getListContractorLookupUseCase.invoke()
        )
    }

    suspend fun getCriskSignoff(taskId: String, criskId: String, index: Int) {
        _apiCallStatus.emit(
            index to getCriskSignoff.invoke(taskId, criskId)
        )
    }

    suspend fun getCrisk(criskId: String, index: Int) {
        _apiCallStatus.emit(index to getCriskUseCase.invoke(criskId))
    }

    suspend fun getCriskEvidence(criskId: String, index: Int) {
        _apiCallStatus.emit(index to getCriskEvidence.invoke(criskId))
    }

    suspend fun archiveCrisk(criskId: String, payload: CriskArchivePayload, index: Int) {
        _apiCallStatus.emit(index to archiveCrisk.invoke(criskId, payload))
    }

    // Region Visitor
    private val fetchSiteUseCase: FetchSiteUseCase by koinInject()
    private val submitQRUseCase: SubmitQRCodeUseCase by koinInject()

    // 1. signInFromQRCode
    suspend fun signIn(qrCode: String, index: Int) {
        _apiCallStatus.emit(index to signInFromQRCode(qrCode))
    }

    private suspend fun signInFromQRCode(qrCode: String): Result<Destination> {
        return submitQRUseCase.invoke(
            qrCode = qrCode,
            destination = { Destination.PinCode(qrCode) }
        ).flatMap { token ->
            fetchSiteUseCase.invoke(VisitorPayload.SiteFetch(token.token!!)) // non null, already handled in submitQR
                .flatMap {
                    Result.Success(
                        Destination.VisitorWizard(
                            site = it,
                            token = token.token,
                            evidence = null // this is sign in flow, so this be null
                        )
                    )
                }
        }.flatMapError {
            when (it) {
                is SCError.NoNetwork, is SCError.InvalidQRCodeRequest -> Result.Error(it)
                else -> Result.Error(SCError.InvalidQRCodeRequest())
            }
        }
    }

    private val fetchEvidenceUseCase: FetchEvidenceUseCase by koinInject()

    // 2. Not tested yet region

    // invokeSignOutFlowFromNotification
    suspend fun signout(requestEvidenceId: String, index: Int) {
        _apiCallStatus.emit(index to signOut(requestEvidenceId))
    }

    private suspend fun signOut(requestEvidenceId: String): Result.Success<Destination> {
        var destination: Destination? = null
        fetchEvidenceUseCase.invoke(requestEvidenceId)
            .doOnSucceed { nEvidence ->
                // TODO("removeGeofence")
                destination = if (nEvidence.leave != null) {
                    Destination.Toast(
                        scError = SCError.Failure(
                            message = nEvidence.site.tier.VISIT_TERMS.leave.toLowerCase(Locale.ROOT)
                        )
                    )
                } else {
                    Destination.VisitorWizard(
                        site = nEvidence.site,
                        evidence = nEvidence,
                        token = null
                    )
                }
            }.doOnFailure {
                destination = Destination.Toast(scError = this) // Show toast: unable to Perform Sign Out because...
            }

        return Result.Success(destination!!)
    }

    private val updateSiteByFormFetchUseCase: UpdateSiteByFormFetchUseCase by koinInject()

    suspend fun fetchLeaveForm(
        token: String?,
        site: VisitorSite?,
        role: VisitorRole?
    ): Result<VisitorSite> {
        token ?: return errorOf("Invalid Token")
        role ?: return errorOf("No selected role.")
        val leaveFormId = site?.getLeaveFormId(role._id) ?: return errorOf("No Leave Form Available.")
        return updateSiteByFormFetchUseCase.invoke(
            payload = VisitorPayload.FormFetch(
                token,
                leaveFormId
            ),
            site = site
        )
    }

    suspend fun fetchArriveForm(
        token: String?,
        role: VisitorRole?,
        site: VisitorSite?
    ): Result<VisitorSite> {
        token ?: return errorOf("Invalid Token")
        role ?: return errorOf("No selected role.")
        val arriveFormId = site?.getArriveFormId(role._id) ?: return errorOf("No Arrive Form Available")

        return updateSiteByFormFetchUseCase.invoke(
            payload = VisitorPayload.FormFetch(
                token,
                arriveFormId
            ),
            site = site
        )
    }

    private val arriveUseCase: ArriveAndUpdateUseCase by koinInject()

    suspend fun submitArriveForm(
        token: String?,
        form: VisitorForm,
        profile: VisitorProfile,
        site: VisitorSite
    ): Result<VisitorEvidence> {
        form.selectedRole ?: return errorOf("Arrive Form has no selected Role. Please assign it before submitting the form")
        token ?: errorOf("No sufficient data available to perform submitForm")
        return arriveUseCase.invoke(
            payload = VisitorPayload.Arrive(
                token!!,
                arrive = form.toPayload(),
                visitor = profile.toPayload(role = form.selectedRole!!)
            ),
            site = site,
            profile = profile
        )
    }
}
