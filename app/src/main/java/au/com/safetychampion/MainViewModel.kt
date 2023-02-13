package au.com.safetychampion

import PrepareSignoffActionUseCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.TaskAssignStatusItem
import au.com.safetychampion.data.domain.models.action.network.ActionPL
import au.com.safetychampion.data.domain.models.action.network.ActionSignOff
import au.com.safetychampion.data.domain.models.chemical.ChemicalSignoff
import au.com.safetychampion.data.domain.models.crisk.CriskArchivePayload
import au.com.safetychampion.data.domain.models.task.Task
import au.com.safetychampion.data.domain.usecase.action.* // ktlint-disable no-wildcard-imports
import au.com.safetychampion.data.domain.usecase.activetask.AssignTaskUseCase
import au.com.safetychampion.data.domain.usecase.activetask.GetAllActiveTaskUseCase
import au.com.safetychampion.data.domain.usecase.activetask.UnAssignTaskUseCase
import au.com.safetychampion.data.domain.usecase.assigntaskstatus.AssignManyTasksStatusItemUseCase
import au.com.safetychampion.data.domain.usecase.assigntaskstatus.AssignTaskStatusItemUseCase
import au.com.safetychampion.data.domain.usecase.banner.GetListBannerUseCase
import au.com.safetychampion.data.domain.usecase.chemical.* // ktlint-disable no-wildcard-imports
import au.com.safetychampion.data.domain.usecase.crisk.* // ktlint-disable no-wildcard-imports
import au.com.safetychampion.data.util.extension.koinInject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

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
}
