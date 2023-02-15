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
import au.com.safetychampion.data.util.extension.koinGet
import au.com.safetychampion.data.util.extension.toJsonString
import au.com.safetychampion.databinding.ActivityMainBinding
import au.com.safetychampion.util.AssetsManager
import kotlinx.coroutines.* // ktlint-disable no-wildcard-imports
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

private fun getToken() = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOnsidHlwZSI6ImNvcmUudXNlciIsIl9pZCI6IjYyNGQyNzVmMDJiZmNhNWJhODkzYWUzNyJ9LCJpYXQiOjE2NzYzNjE5NzgsImV4cCI6MTY3NjQ0ODM3OH0.65ZCAChq3imDES5GNo_N_JtkChhc73rAxcwKnx3jb-8"
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
        "Create new action" to suspend { viewModel.createNewAction(payload = sampleData.getNewAction(), index = 6) },
        "List Action" to suspend { viewModel.getListAction(7) },
        "Get Action SignOff" to suspend { viewModel.getActionSignOff(actionId = sampleData.getActionId(), id = sampleData.getSampleTask()._id, index = 8) },
        "Edit Action" to suspend {
            viewModel.editAction(
                actionPL = sampleData.getEditAction(),
                id = sampleData.getEditAction()._id!!,
                index = 9
            )
        },
        "Get List Banner" to suspend { viewModel.getListBanner(10) },
        "Edit Action" to suspend {
            viewModel.editAction(
                actionPL = sampleData.getEditAction(),
                id = sampleData.getEditAction()._id!!,
                index = 11
            )
        },
        "Refresh GHS code" to suspend { viewModel.refreshGHS(12) },
        "Refresh Chemical" to suspend { viewModel.refreshChemical(13) },
        "Get Chemical Signoff" to suspend {
            viewModel.getChemicalSignoff(
                moduleId = "61ad7aedb3ea32726aac3523",
                id = "0123456",
                index = 14
            )
        },

//        "Signoff Action" to suspend {
//            viewModel.signOffAction(
//                actionSignOff = TODO("Add sample actionSignoff"),
//                index = 15
//            )
//        },
//        "Signoff Chemical" to suspend {
//            viewModel.signoffChemical(
//                signoff = TODO("Add sample chemicalSignoff"),
//                index = 16
//
//            )
//        },
        "Get Crisk List" to suspend { viewModel.getListCrisk(15) },
        "Get Crisk HrLookup" to suspend { viewModel.getListHrLookup(16) },
        "Get Crisk Contractor Lookup" to suspend { viewModel.getListContractorLookup(17) },
        "Get Crisk Signoff" to suspend { viewModel.getCriskSignoff(taskId = "63a2584497f7ee1e8d3d6369", criskId = "633fa33f4d59ca38fe91336e", index = 18) },
        "Get Crisk" to suspend { viewModel.getCrisk(criskId = "633fa33f4d59ca38fe91336e", index = 19) },
        "Crisk Evidence" to suspend { viewModel.getCriskEvidence(criskId = "633fa33f4d59ca38fe91336e", index = 20) },
        "Archive Crisk" to suspend { viewModel.archiveCrisk(criskId = "633fa33f4d59ca38fe91336e", payload = sampleData.getCriskArchivePL(), index = 21) },
        "QR CODE Visitor" to suspend {
            viewModel.signIn(qrCode = "/org/5efbeb98c6bac31619e11bc9/site/616f824aee1dfb288ad8cf55", index = 22)
        },
        "Fetch Copy source" to suspend { viewModel.copySource("63ec866dde4d671748fe6a91", 23) },
        "Fetch List Document" to suspend { viewModel.fetchListDoc("63ec866dde4d671748fe6a91", 24) },
        "Fetch Document" to suspend { viewModel.fetchDoc("63ec866dde4d671748fe6a91", 25) }
//        "Signoff Document" to suspend { viewModel.signoffDoc(payload = sampleData.getSignoffChemical(), 26) } TODO("Add valid sample signoff")
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
                    launch { it.second.invoke() }
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
