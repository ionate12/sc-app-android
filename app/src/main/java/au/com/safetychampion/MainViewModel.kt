package au.com.safetychampion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.TaskAssignStatusItem
import au.com.safetychampion.data.domain.models.task.Task
import au.com.safetychampion.data.domain.usecase.activetask.AssignTaskUseCase
import au.com.safetychampion.data.domain.usecase.activetask.GetAllActiveTaskUseCase
import au.com.safetychampion.data.domain.usecase.activetask.UnAssignTaskUseCase
import au.com.safetychampion.data.domain.usecase.assigntaskstatus.AssignManyTasksStatusItemUseCase
import au.com.safetychampion.data.domain.usecase.assigntaskstatus.AssignTaskStatusItemUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val getActiveTaskUseCase: GetAllActiveTaskUseCase,
    private val assignTaskStatusItem: AssignTaskStatusItemUseCase,
    private val assignTasksStatusItem: AssignManyTasksStatusItemUseCase,
    private val assignTaskUseCase: AssignTaskUseCase,
    private val unAssignTaskUseCase: UnAssignTaskUseCase
) : ViewModel() {

    private val _apiCallStatus = MutableSharedFlow<Result<*>>()
    val apiCallStatus = _apiCallStatus.asSharedFlow()

    private val _payload = MutableStateFlow<String?>(null)
    val payload = _payload.asStateFlow()

    private suspend fun onAPIcall(result: Result<*>) {
        _apiCallStatus.emit(result)
    }

    fun loadActiveTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            _apiCallStatus.emit(Result.Loading)
            val result = getActiveTaskUseCase.invoke(null)
            onAPIcall(result)
        }
    }

    fun loadActiveTasksReViewPlan() {
        viewModelScope.launch(Dispatchers.IO) {
            _apiCallStatus.emit(Result.Loading)
            val result = getActiveTaskUseCase.invoke("core.module.reviewplan")
            onAPIcall(result)
        }
    }

    fun assignTaskStatus(task: Task) {
        viewModelScope.launch {
            _apiCallStatus.emit(Result.Loading)
            val result = assignTaskStatusItem.invoke(task, null, null, null, null)
            onAPIcall(result)
        }
    }

    fun assignTaskStatusForMany(task: List<Task>) {
        viewModelScope.launch {
            _apiCallStatus.emit(Result.Loading)
            val result = assignTasksStatusItem.invoke(task)
            onAPIcall(result)
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
            onAPIcall(result)
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
            onAPIcall(result)
        }
    }
}
