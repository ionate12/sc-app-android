package au.com.safetychampion

import android.R
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import au.com.Adapter
import au.com.Item
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.core.dataOrNull
import au.com.safetychampion.data.domain.core.errorOrNull
import au.com.safetychampion.data.domain.manager.ITokenManager
import au.com.safetychampion.data.domain.uncategory.AppToken
import au.com.safetychampion.data.util.extension.toJsonString
import au.com.safetychampion.databinding.ActivityMainBinding
import au.com.safetychampion.util.AssetsManager
import au.com.safetychampion.util.koinGet
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

private fun getToken() = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOnsidHlwZSI6ImNvcmUudXNlciIsIl9pZCI6IjVlZmJlYmE0YzZiYWMzMTYxOWUxMWJlNCJ9LCJpYXQiOjE2NzU5NTYwOTksImV4cCI6MTY3NjA0MjQ5OX0.uOnWA2T10pYzkXDTkJmF0oERt7-rNWWjqpITcNpCxmQ"
var testAll = true
class MainActivity : AppCompatActivity() {
    private val viewModel by viewModel<MainViewModel>()
    private val sampleData = AssetsManager(this)
    private lateinit var binding: ActivityMainBinding
    private val listUseCase = listOf(
        "Get Active Task (tasks/list/active)" to suspend { viewModel.loadActiveTasks(0) },
        "Load Active Tasks ReViewPlan (tasks/list/active)" to suspend { viewModel.loadActiveTasksReViewPlan(1) },
        "Assign Task Status (tasks/assign/status)" to suspend { viewModel.assignTaskStatus(sampleData.getSampleTask(), 2) },
        "Assign Task Status Many (tasks/assign/status)" to suspend { viewModel.assignTaskStatusForMany(sampleData.getListSampleTask(), 3) },
        "Assign Task" to suspend { viewModel.assignTask(ownerTask = sampleData.getSampleTask(), assignTask = sampleData.getSampleTaskAssignStatusItem(), index = 4) },
        "UnAssign Task" to suspend { viewModel.unAssignTask(ownerTask = sampleData.getSampleTask(), assignTask = sampleData.getSampleTaskAssignStatusItem(), index = 5) },
        "Create new action" to suspend { viewModel.createNewAction(payload = sampleData.getNewAction(), attachments = emptyList(), index = 6) },
        "List Action" to suspend { viewModel.getListAction(7) },
        "Get Action SignOff" to suspend { viewModel.getActionSignOff(actionId = sampleData.getActionId(), id = sampleData.getSampleTask()._id, index = 8) },
        "Edit Action" to suspend { viewModel.editAction(actionPL = sampleData.getEditAction(), id = sampleData.getEditAction()._id!!, attachments = emptyList(), index = 9) },
        "Get List Banner" to suspend { viewModel.getListBanner(10) },
        "Edit Action" to suspend { viewModel.editAction(actionPL = sampleData.getEditAction(), id = sampleData.getEditAction()._id!!, attachments = emptyList(), index = 11) },
        "Refresh GHS code" to suspend { viewModel.refreshGHS(12) },
        "Refresh Chemical" to suspend { viewModel.refreshChemical(13) },
        "Get Chemical Signoff" to suspend {
            viewModel.getChemicalSignoff(
                moduleId = "61ad7aedb3ea32726aac3523",
                id = "0123456",
                index = 14
            )
        },

        "Signoff Action" to suspend {
            viewModel.signOffAction(
                actionId = sampleData.getActionId(),
                attachments = emptyList(),
                payload = sampleData.getActionTask(),
                pendingAction = sampleData.getPendingActionPL(),
                index = 15
            )
        },
        "Signoff Chemical" to suspend {
            viewModel.signoffChemical(
                taskId = "61ad7aedb3ea32726aac3522",
                moduleId = "61ad7aedb3ea32726aac3523",
                task = sampleData.getChemicalTask(),
                attachments = emptyList(),
                index = 16

            )
        },
        "Fetch Contractor" to suspend {
            viewModel.fetchContractor(
                index = 17,
                moduleId = "5efbedcac6bac31619e1221e"
            )
        },
        "Get List Contractor" to suspend {
            viewModel.getListContractor(18)
        },
        "Get Linked Contractor" to suspend {
            viewModel.getLinkedTask(19, payload = sampleData.getLinkedTaskPayload())
        }
    )

    val items = {
        listUseCase.map {
            Item(
                path = it.first,
                status = "Loading",
                loading = true,
                result = ""
            )
        }
    }

    val mAdpater = Adapter()
    private var loadAllJob: Job? = null

    fun initRecycleview() {
        mAdpater.testAgainn = {
            testAll = false
            loadAllJob?.cancel()
            mAdpater.list.clear()
            mAdpater.notifyDataSetChanged()
            lifecycleScope.launch {
                viewModel._apiCallStatus.emit(0 to Result.Loading)
            }
            lifecycleScope.launch { listUseCase.get(it).second.invoke() }
        }
        binding.recyclerView.adapter = mAdpater
        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // Append Token Manually
        CoroutineScope(dispatchers().main).launch {
            koinGet<ITokenManager>().updateToken(AppToken.Authed(getToken()))
        }
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecycleview()

        val spinnerArrayAdapter: ArrayAdapter<*> = ArrayAdapter<Any?>(
            this,
            R.layout.simple_spinner_dropdown_item,
            listUseCase.map { it.first }
        )

        val clipboard: ClipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        binding.copy.setOnClickListener {
            val clip = ClipData.newPlainText("text", binding.result.text)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this, "Copied", Toast.LENGTH_LONG).show()
        }
        binding.api.adapter = (spinnerArrayAdapter)

        binding.test.setOnClickListener {
            testAll = false
            binding.testAll.isEnabled = false
            lifecycleScope.launch {
                viewModel._apiCallStatus.emit(0 to Result.Loading)
                listUseCase[binding.api.selectedItemPosition].second.invoke()
            }
        }
        binding.testAll.setOnClickListener {
            testAll = true
            binding.test.isEnabled = false
            mAdpater.list.clear()
            mAdpater.list.addAll(
                items()
            )
            mAdpater.notifyDataSetChanged()

            loadAllJob = lifecycleScope.launch(Dispatchers.Default) {
                listUseCase.forEach {
                    it.second.invoke()
                }
            }
        }

        lifecycleScope.launch {
            viewModel.apiCallStatus.collectLatest {
                if (testAll) {
                    val i = it.first
                    val rs = it.second
                    mAdpater.list[i].apply {
                        this.loading = (false)
                        this.status = (
                            if (rs is Result.Success) "Success" else "Error"
                            )
                        this.color = if (rs is Result.Success) au.com.safetychampion.R.color.success else au.com.safetychampion.R.color.error
                        this.result = (it.second.dataOrNull()?.toJsonString() ?: it.second.errorOrNull()?.toString()!!)
                        this.testAgain = if (rs is Result.Error) View.VISIBLE else View.GONE
                        mAdpater.notifyItemChanged(i)
                    }
                } else {
                    binding.progress.isVisible = it.second is Result.Loading
                    when (it.second) {
                        is Result.Loading -> {
                            binding.status.text = "This is Result.Loading"
                            binding.result.text = ""
                            binding.count.text = "[]"
                        }
                        is Result.Error -> {
                            binding.status.text = "This is Result.ERROR"
                            binding.result.text = (it.second as Result.Error).err.toString()
                        }
                        is Result.Success -> {
                            binding.status.text = "This is Result.Success"
                            binding.result.text = (it.second as Result.Success<*>).data?.toJsonString()
                            binding.count.text = "[${((it.second as Result.Success<*>).data as? List<*>)?.size}]"
                        }
                    }
                }
            }
        }

        binding.result.movementMethod = ScrollingMovementMethod()
    }
}
