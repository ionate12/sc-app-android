package au.com.safetychampion

import GetActionSignOffDetailsUseCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import au.com.safetychampion.data.domain.Attachment
import au.com.safetychampion.data.domain.core.* // ktlint-disable no-wildcard-imports
import au.com.safetychampion.data.domain.models.TaskAssignStatusItem
import au.com.safetychampion.data.domain.models.action.ActionTask
import au.com.safetychampion.data.domain.models.action.network.ActionPL
import au.com.safetychampion.data.domain.models.action.network.PendingActionPL
import au.com.safetychampion.data.domain.models.chemical.ChemicalTask
import au.com.safetychampion.data.domain.models.task.Task
import au.com.safetychampion.data.domain.usecase.action.* // ktlint-disable no-wildcard-imports
import au.com.safetychampion.data.domain.usecase.activetask.AssignTaskUseCase
import au.com.safetychampion.data.domain.usecase.activetask.GetAllActiveTaskUseCase
import au.com.safetychampion.data.domain.usecase.activetask.UnAssignTaskUseCase
import au.com.safetychampion.data.domain.usecase.assigntaskstatus.AssignManyTasksStatusItemUseCase
import au.com.safetychampion.data.domain.usecase.assigntaskstatus.AssignTaskStatusItemUseCase
import au.com.safetychampion.data.domain.usecase.banner.GetListBannerUseCase
import au.com.safetychampion.data.domain.usecase.chemical.* // ktlint-disable no-wildcard-imports
import au.com.safetychampion.data.visitor.domain.models.* // ktlint-disable no-wildcard-imports
import au.com.safetychampion.data.visitor.domain.usecase.ArriveAndUpdateUseCase
import au.com.safetychampion.data.visitor.domain.usecase.evidence.FetchEvidenceUseCase
import au.com.safetychampion.data.visitor.domain.usecase.qr.SubmitQRCodeUseCase
import au.com.safetychampion.data.visitor.domain.usecase.site.FetchSiteUseCase
import au.com.safetychampion.data.visitor.domain.usecase.site.UpdateSiteByFormFetchUseCase
import au.com.safetychampion.util.koinInject
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
    private val getActionSignOffDetailsUseCase: GetActionSignOffDetailsUseCase by koinInject()
    private val editActionUseCase: EditActionUseCase by koinInject()
    private val signOffActionUseCase: SignoffActionUseCase by koinInject()

    private val getListBannerUseCase: GetListBannerUseCase by koinInject()
    private val getChemicalSignoffDetailsUseCase: GetChemicalSignoffDetailUseCase by koinInject()
    private val refreshGHSCodeUseCase: RefreshGHSCodeUseCase by koinInject()
    private val refreshChemicalUseCase: RefreshChemicalListUseCase by koinInject()
    private val signoffChemicalUseCase: SignoffChemicalUseCase by koinInject()

    val _apiCallStatus = MutableSharedFlow<Pair<Int, Result<*>>>()
    val apiCallStatus = _apiCallStatus.asSharedFlow()

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

    suspend fun createNewAction(payload: ActionPL, attachments: List<Attachment>, index: Int) {
        _apiCallStatus.emit(
            index to newActionUseCase.invoke(
                payload = payload,
                attachments = attachments
            )
        )
    }

    suspend fun getListAction(index: Int) {
        _apiCallStatus.emit(index to allAction.invoke())
    }

    suspend fun getActionSignOff(actionId: String, id: String, index: Int) {
        _apiCallStatus.emit(
            index to Result.Error(
                SCError.Failure(listOf("Error, bug in custom value"))
            )
        )
//            getActionSignOffDetailsUseCase.invoke(id, actionId)
    }

    suspend fun editAction(actionPL: ActionPL, id: String, attachments: List<Attachment>, index: Int) {
        _apiCallStatus.emit(index to editActionUseCase.invoke(id, actionPL, attachments))
    }

    suspend fun signOffAction(
        actionId: String,
        attachments: List<Attachment>,
        payload: ActionTask,
        pendingAction: MutableList<PendingActionPL>,
        index: Int
    ) {
        _apiCallStatus.emit(
            index to signOffActionUseCase.invoke(
                ActionSignOffParam(
                    actionId = actionId,
                    attachments = attachments,
                    payload = payload,
                    pendingAction = pendingAction,
                    id = "ABC"
                )
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
        refreshGHSCodeUseCase.invoke()
        _apiCallStatus.emit(index to Result.Success("Done. Please check the logcat for more info"))
    }

    suspend fun refreshChemical(index: Int) {
        viewModelScope.launch {
            refreshChemicalUseCase.invoke()
            _apiCallStatus.emit(index to Result.Success("Done. Please check the logcat for more info"))
        }
    }

    suspend fun signoffChemical(
        taskId: String,
        moduleId: String,
        task: ChemicalTask,
        attachments: List<Attachment>,
        index: Int
    ) {
        _apiCallStatus.emit(
            index to signoffChemicalUseCase.invoke(
                ChemicalSignoffParam(
                    taskId = taskId,
                    moduleId = moduleId,
                    task = task,
                    attachments = attachments
                )
            )
        )
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

        return Result.Success(destination)
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
