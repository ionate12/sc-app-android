package au.com.safetychampion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import au.com.safetychampion.data.domain.Attachment
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.TaskAssignStatusItem
import au.com.safetychampion.data.domain.models.task.Task
import au.com.safetychampion.data.domain.payload.ActionPojo
import au.com.safetychampion.data.domain.usecase.action.* // ktlint-disable no-wildcard-imports
import au.com.safetychampion.data.domain.usecase.activetask.AssignTaskUseCase
import au.com.safetychampion.data.domain.usecase.activetask.GetAllActiveTaskUseCase
import au.com.safetychampion.data.domain.usecase.activetask.UnAssignTaskUseCase
import au.com.safetychampion.data.domain.usecase.assigntaskstatus.AssignManyTasksStatusItemUseCase
import au.com.safetychampion.data.domain.usecase.assigntaskstatus.AssignTaskStatusItemUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val getActiveTaskUseCase: GetAllActiveTaskUseCase,
    private val assignTaskStatusItem: AssignTaskStatusItemUseCase,
    private val assignTasksStatusItem: AssignManyTasksStatusItemUseCase,
    private val assignTaskUseCase: AssignTaskUseCase,
    private val unAssignTaskUseCase: UnAssignTaskUseCase,

    private val newActionUseCase: CreateNewActionUseCase,
    private val allAction: GetListActionUseCase,
    private val getActionSignOffDetailsUseCase: GetActionSignOffDetailsUseCase,
    private val editActionUseCase: EditActionUseCase
) : ViewModel() {

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

    fun createNewAction(payload: ActionPojo, attachments: List<Attachment>) {
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

    fun getActionSignOff(actionId: String) {
        viewModelScope.launch {
            _apiCallStatus.emit(Result.Loading)
            _apiCallStatus.emit(getActionSignOffDetailsUseCase.invoke(actionId))
        }
    }

    fun editAction(actionPL: ActionPojo, id: String, attachments: List<Attachment>) {
        viewModelScope.launch {
            _apiCallStatus.emit(Result.Loading)
            _apiCallStatus.emit(editActionUseCase.invoke(id, actionPL, attachments))
        }
    }
}
