package au.com.safetychampion

import GetActionSignOffDetailsUseCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import au.com.safetychampion.data.domain.Attachment
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.TaskAssignStatusItem
import au.com.safetychampion.data.domain.models.action.ActionTask
import au.com.safetychampion.data.domain.models.action.network.ActionPL
import au.com.safetychampion.data.domain.models.action.network.PendingActionPL
import au.com.safetychampion.data.domain.models.task.Task
import au.com.safetychampion.data.domain.usecase.action.* // ktlint-disable no-wildcard-imports
import au.com.safetychampion.data.domain.usecase.activetask.AssignTaskUseCase
import au.com.safetychampion.data.domain.usecase.activetask.GetAllActiveTaskUseCase
import au.com.safetychampion.data.domain.usecase.activetask.UnAssignTaskUseCase
import au.com.safetychampion.data.domain.usecase.assigntaskstatus.AssignManyTasksStatusItemUseCase
import au.com.safetychampion.data.domain.usecase.assigntaskstatus.AssignTaskStatusItemUseCase
import au.com.safetychampion.data.domain.usecase.banner.GetListBannerUseCase
import au.com.safetychampion.util.koinInject
import kotlinx.coroutines.Dispatchers
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
    private val getActionSignOffDetailsUseCase: GetActionSignOffDetailsUseCase by koinInject()
    private val editActionUseCase: EditActionUseCase by koinInject()
    private val signOffActionUseCase: SignOffActionUseCase by koinInject()

    private val getListBannerUseCase: GetListBannerUseCase by koinInject()

    private val _apiCallStatus = MutableSharedFlow<Result<*>>()
    val apiCallStatus = _apiCallStatus.asSharedFlow()

    fun loadActiveTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            _apiCallStatus.emit(Result.Loading)
            val result = getActiveTaskUseCase.invoke(null)
            _apiCallStatus.emit(result)
        }
    }

    fun loadActiveTasksReViewPlan() {
        viewModelScope.launch(Dispatchers.IO) {
            _apiCallStatus.emit(Result.Loading)
            val result = getActiveTaskUseCase.invoke("core.module.reviewplan")
            _apiCallStatus.emit(result)
        }
    }

    fun assignTaskStatus(task: Task) {
        viewModelScope.launch {
            _apiCallStatus.emit(Result.Loading)
            val result = assignTaskStatusItem.invoke(task, null, null, null, null)
            _apiCallStatus.emit(result)
        }
    }

    fun assignTaskStatusForMany(task: List<Task>) {
        viewModelScope.launch {
            _apiCallStatus.emit(Result.Loading)
            val result = assignTasksStatusItem.invoke(task)
            _apiCallStatus.emit(result)
        }
    }

    fun assignTask(assignTask: TaskAssignStatusItem, ownerTask: Task) {
        viewModelScope.launch {
            _apiCallStatus.emit(Result.Loading)
            val result = assignTaskUseCase.invoke(
                task = ownerTask,
                toUserId = assignTask._id,
                moduleName = "Action",
                notes = assignTask.optionalMessage,
                dateDue = ownerTask.dateDue
            )
            _apiCallStatus.emit(result)
        }
    }

    fun unAssignTask(assignTask: TaskAssignStatusItem, ownerTask: Task) {
        viewModelScope.launch {
            _apiCallStatus.emit(Result.Loading)
            val result = unAssignTaskUseCase.invoke(
                task = ownerTask,
                toUserId = assignTask._id,
                moduleName = "Action",
                notes = assignTask.optionalMessage,
                dateDue = ownerTask.dateDue
            )
            _apiCallStatus.emit(result)
        }
    }

    fun createNewAction(payload: ActionPL, attachments: List<Attachment>) {
        viewModelScope.launch {
            _apiCallStatus.emit(Result.Loading)
            _apiCallStatus.emit(
                newActionUseCase.invoke(
                    payload = payload,
                    attachments = attachments
                )
            )
        }
    }

    fun getListAction() {
        viewModelScope.launch {
            _apiCallStatus.emit(Result.Loading)
            _apiCallStatus.emit(allAction.invoke())
        }
    }

    fun getActionSignOff(actionId: String, task: Task) {
        viewModelScope.launch {
            _apiCallStatus.emit(Result.Loading)
            _apiCallStatus.emit(getActionSignOffDetailsUseCase.invoke(task, actionId))
        }
    }

    fun editAction(actionPL: ActionPL, id: String, attachments: List<Attachment>) {
        viewModelScope.launch {
            _apiCallStatus.emit(Result.Loading)
            _apiCallStatus.emit(editActionUseCase.invoke(id, actionPL, attachments))
        }
    }

    fun signOffAction(
        actionId: String,
        attachments: List<Attachment>,
        payload: ActionTask,
        pendingAction: MutableList<PendingActionPL>
    ) {
        viewModelScope.launch {
            signOffActionUseCase.invoke(
                ActionSignOffParam(
                    actionId = actionId,
                    attachments = attachments,
                    payload = payload,
                    pendingAction = pendingAction,
                    id = "ABC"
                )
            )
        }
    }

    fun getListBanner() {
        viewModelScope.launch {
            _apiCallStatus.emit(Result.Loading)
            _apiCallStatus.emit(getListBannerUseCase.invoke())
        }
    }
}
