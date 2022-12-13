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
import au.com.safetychampion.data.domain.core.doOnFailure
import au.com.safetychampion.data.domain.core.doOnSucceed
import au.com.safetychampion.data.domain.usecase.activetask.GetAllActiveTaskUseCase
import au.com.safetychampion.data.domain.usecase.activetask.InsertTaskToDBUseCase
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
    }
}

class MainViewModel : ViewModel() {
    private val api = RetrofitClient.getRetrofit().create(TaskAPI::class.java)
    private val taskRepository: TaskRepository = TaskRepositoryImpl(api)
    private val getActiveTaskUseCase = GetAllActiveTaskUseCase(taskRepository)
    private val insertTaskToDBUseCase = InsertTaskToDBUseCase(taskRepository as TaskDAO)

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
}
