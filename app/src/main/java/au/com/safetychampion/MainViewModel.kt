package au.com.safetychampion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import au.com.safetychampion.data.data.common.TaskAPI
import au.com.safetychampion.data.data.common.TaskRepository
import au.com.safetychampion.data.data.common.TaskRepositoryImpl
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.task.Task
import au.com.safetychampion.data.domain.usecase.activetask.GetAllActiveTaskUseCase
import au.com.safetychampion.data.domain.usecase.assigntaskstatus.AssignManyTasksStatusItemUseCase
import au.com.safetychampion.data.domain.usecase.assigntaskstatus.AssignTaskStatusItemUseCase
import au.com.safetychampion.data.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val api = RetrofitClient.getRetrofit().create(TaskAPI::class.java)
    private val taskRepository: TaskRepository = TaskRepositoryImpl(api)
    private val getActiveTaskUseCase = GetAllActiveTaskUseCase(taskRepository)

    private val assignTaskStatusItem = AssignTaskStatusItemUseCase(taskRepository)
    private val assignTasksStatusItem = AssignManyTasksStatusItemUseCase(taskRepository)

    private val _apiCallStatus = MutableSharedFlow<Result<*>>()
    val apiCallStatus = _apiCallStatus.asSharedFlow()

    private val _payload = MutableStateFlow<String?>(null)
    val payload = _payload.asStateFlow()

    private suspend fun onAPIcall(payload: String, result: Result<*>) {
        _apiCallStatus.emit(result)
        _payload.emit(payload)
    }

    fun loadActiveTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            _apiCallStatus.emit(Result.Loading)
            val result = getActiveTaskUseCase.invoke(null)
            onAPIcall(result.first, result.second)
        }
    }

    fun loadActiveTasksReViewPlan() {
        viewModelScope.launch(Dispatchers.IO) {
            _apiCallStatus.emit(Result.Loading)
            val result = getActiveTaskUseCase.invoke("core.module.reviewplan")
            onAPIcall(result.first, result.second)
        }
    }

    fun assignTaskStatus(task: Task) {
        viewModelScope.launch {
            _apiCallStatus.emit(Result.Loading)
            val result = assignTaskStatusItem.invoke(task, null, null, null, null)
            onAPIcall(result.first, result.second)
        }
    }

    fun assignTaskStatusForMany(task: List<Task>) {
        viewModelScope.launch {
            _apiCallStatus.emit(Result.Loading)
            val result = assignTasksStatusItem.invoke(task)
            onAPIcall(result.first, result.second)
        }
    }
}
