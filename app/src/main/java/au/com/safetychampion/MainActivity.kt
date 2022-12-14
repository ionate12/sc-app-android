package au.com.safetychampion

import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import au.com.safetychampion.data.data.common.TaskAPI
import au.com.safetychampion.data.data.common.TaskDAO
import au.com.safetychampion.data.data.common.TaskRepository
import au.com.safetychampion.data.data.common.TaskRepositoryImpl
import au.com.safetychampion.data.domain.base.BaseModule
import au.com.safetychampion.data.domain.core.doOnFailure
import au.com.safetychampion.data.domain.core.doOnSucceed
import au.com.safetychampion.data.domain.models.Tier
import au.com.safetychampion.data.domain.models.TierType
import au.com.safetychampion.data.domain.models.task.Task
import au.com.safetychampion.data.domain.usecase.activetask.GetAllActiveTaskUseCase
import au.com.safetychampion.data.domain.usecase.activetask.InsertTaskToDBUseCase
import au.com.safetychampion.data.domain.usecase.assigntaskstatus.AssignTaskStatusItemUseCase
import au.com.safetychampion.data.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.active).setOnClickListener {
            viewModel.loadActiveTasksReViewPlan()
        }

        findViewById<Button>(R.id.active1).setOnClickListener {
            viewModel.loadActiveTasks()
        }

        findViewById<Button>(R.id.active2).setOnClickListener {
            viewModel.fetchAssignTaskStatus(
                Task(
                    title = "Corrective Action: ACT0830",
                    _id = "6116145ca5c4171b30dc66a9",
                    _for = object : BaseModule {
                        override val _id: String = "6116145ca5c4171b30dc66aa"
                        override val type: String = "core.module.action"
                    },
                    type = "core.module.task",
                    tier = Tier(
                        _id = "5efbeb98c6bac31619e11bc9",
                        type = TierType.T3
                    ),
                    description = "This is ORG's Action",
                    dateDue = "Mon Feb 20 00:00:00 GMT+07:00 2017",
                    complete = false,
                    inExecution = false
                )
            )
        }
    }
}

class MainViewModel : ViewModel() {
    private val api = RetrofitClient.getRetrofit().create(TaskAPI::class.java)
    private val taskRepository: TaskRepository = TaskRepositoryImpl(api)
    private val getActiveTaskUseCase = GetAllActiveTaskUseCase(taskRepository)
    private val insertTaskToDBUseCase = InsertTaskToDBUseCase(taskRepository as TaskDAO)
    private val assignTaskStatusItem = AssignTaskStatusItemUseCase(taskRepository)

    fun loadActiveTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getActiveTaskUseCase.invoke(null)
            result.doOnSucceed(insertTaskToDBUseCase::invoke)
            result.doOnFailure {
                // update UI error...
                println(this.toString())
            }
        }
    }

    fun loadActiveTasksReViewPlan() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getActiveTaskUseCase.invoke("core.module.reviewplan")
            result.doOnSucceed(insertTaskToDBUseCase::invoke)
            result.doOnFailure {
                // update UI error...
                println(this.errDescription)
            }
        }
    }

    fun fetchAssignTaskStatus(task: Task) {
        viewModelScope.launch {
            val result = assignTaskStatusItem.invoke(task, null, null, null, null)

            result.doOnSucceed {
                println("fetchAssignTaskStatus succeed: $size")
            }

            result.doOnFailure {
                println(this)
            }
        }
    }
}
