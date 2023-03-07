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
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import au.com.Adapter
import au.com.Item
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.core.dataOrNull
import au.com.safetychampion.data.domain.core.errorOrNull
import au.com.safetychampion.data.domain.manager.IUserInfoManager
import au.com.safetychampion.data.util.extension.koinGet
import au.com.safetychampion.data.util.extension.toJsonString
import au.com.safetychampion.databinding.ActivityMainBinding
import au.com.safetychampion.util.AssetsManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

var testAll = true
class MainActivity : AppCompatActivity() {
    private val viewModel by viewModel<MainViewModel>()
    private val sampleData = AssetsManager(this)
    private lateinit var binding: ActivityMainBinding
    private var counter = -1
    private val listUseCase = listOf(
        "Login as u3_2@minh1.co" to suspend { viewModel.login(++counter) },
        "Login Multi w demomanager@safetychampion.online " to suspend { viewModel.multiLogin(++counter) },
        "Morph (required login as demomanager) " to suspend { viewModel.morph(++counter) },
        "UnMorph (required  morph) " to suspend { viewModel.unmorph(++counter) },
        "Sign out" to suspend {
            viewModel.logout(++counter)
            checkUserData()
        },
        "Get Active Task (tasks/list/active)" to suspend { viewModel.loadActiveTasks(++counter) },
        "Load Active Tasks ReViewPlan (tasks/list/active)" to suspend { viewModel.loadActiveTasksReViewPlan(++counter) },
        "Assign Task Status (tasks/assign/status)" to suspend { viewModel.assignTaskStatus(sampleData.getSampleTask(), ++counter) },
        "Assign Task Status Many (tasks/assign/status)" to suspend { viewModel.assignTaskStatusForMany(sampleData.getListSampleTask(), ++counter) },
        "Assign Task" to suspend { viewModel.assignTask(ownerTask = sampleData.getSampleTask(), assignTask = sampleData.getSampleTaskAssignStatusItem(), index = ++counter) },
        "UnAssign Task" to suspend { viewModel.unAssignTask(ownerTask = sampleData.getSampleTask(), assignTask = sampleData.getSampleTaskAssignStatusItem(), index = ++counter) },
        "Create new action" to suspend { viewModel.createNewAction(payload = sampleData.getNewAction(), index = ++counter) },
        "List Action" to suspend { viewModel.getListAction(++counter) },
        "Get Action SignOff" to suspend { viewModel.getActionSignOff(actionId = sampleData.getActionId(), id = sampleData.getSampleTask()._id, index = ++counter) },
        "Edit Action" to suspend {
            viewModel.editAction(
                actionPL = sampleData.getEditAction(),
                id = sampleData.getEditAction()._id!!,
                index = ++counter,
            )
        },
        "Get List Banner" to suspend { viewModel.getListBanner(++counter) },
        "Edit Action" to suspend {
            viewModel.editAction(
                actionPL = sampleData.getEditAction(),
                id = sampleData.getEditAction()._id!!,
                index = 11,
            )
        },
        "Refresh GHS code" to suspend { viewModel.refreshGHS(++counter) },
        "Refresh Chemical" to suspend { viewModel.refreshChemical(++counter) },
        "Get Chemical Signoff" to suspend {
            viewModel.getChemicalSignoff(
                moduleId = "61ad7aedb3ea32726aac3523",
                id = "0123456",
                index = ++counter,
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
        "Get Crisk List" to suspend { viewModel.getListCrisk(++counter) },
        "Get Crisk HrLookup" to suspend { viewModel.getListHrLookup(++counter) },
        "Get Crisk Contractor Lookup" to suspend { viewModel.getListContractorLookup(++counter) },
        "Get Crisk Signoff" to suspend { viewModel.getCriskSignoff(taskId = "63a2584497f7ee1e8d3d6369", criskId = "633fa33f4d59ca38fe91336e", index = ++counter) },
        "Get Crisk" to suspend { viewModel.getCrisk(criskId = "633fa33f4d59ca38fe91336e", index = ++counter) },
        "Crisk Evidence" to suspend { viewModel.getCriskEvidence(criskId = "633fa33f4d59ca38fe91336e", index = ++counter) },
        "Archive Crisk" to suspend { viewModel.archiveCrisk(criskId = "633fa33f4d59ca38fe91336e", payload = sampleData.getCriskArchivePL(), index = ++counter) },
        "QR CODE Visitor" to suspend {
            viewModel.signIn(qrCode = "/org/5efbeb98c6bac31619e11bc9/site/616f824aee1dfb288ad8cf55", index = ++counter)
        },
        "Fetch Copy source" to suspend { viewModel.copySource("63ec866dde4d671748fe6a91", ++counter) },
        "Fetch List Document" to suspend { viewModel.fetchListDoc("63ec866dde4d671748fe6a91", ++counter) },
        "Fetch Document" to suspend { viewModel.fetchDoc("63ec866dde4d671748fe6a91", ++counter) },
        "Create incident" to suspend { viewModel.createIncident(payload = sampleData.getNewIncident(), ++counter) },
        "Fetch Incident" to suspend { viewModel.fetchIncident(moduleId = "630d5de6ca562f742c1a2988", ++counter) },
        "List Incident" to suspend { viewModel.fetchListIncident(++counter) },
//        "Lookup incident" to suspend { viewModel.lookupIncident(29) }, // TODO("crash hrLookUp, have not consistent in response, maybe need custom adapter")
        "Prepare Incident" to suspend { viewModel.prepareIncident(moduleId = "630d5de6ca562f742c1a2988", taskId = "630d5de6ca562f742c1a2988", ++counter) },
//
//        "Signoff Document" to suspend { viewModel.signoffDoc(payload = sampleData.getSignoffChemical(), 26) } TODO("Add valid sample signoff")
        "fetchBlock" to suspend { viewModel.fetchBlock("62ecff3130e68b29607353f9", 30) },
        "fetchBoards" to suspend { viewModel.fetchBoards(31) },
        "fetchVdocNoticeboard" to suspend { viewModel.fetchVdocNoticeboard("62ecff3130e68b29607353f9", 32) },
        "fetchNoticeboardForms" to suspend { viewModel.fetchNoticeboardForms(listOf(), 33) } // TODO("Need add more form in u3_2@minh1.co")
//      "Submit form" to suspend { viewModel.submitNoticeboardForms() } //
    )

    val items = {
        listUseCase.map {
            Item(
                path = it.first,
                status = "Loading",
                loading = true,
                result = "",
            )
        }
    }

    val mAdpater = Adapter()
    private var loadAllJob: Job? = null

    private val dispatchers = Dispatchers.Main + CoroutineExceptionHandler { coroutineContext, throwable ->
        binding.result.apply {
            text = throwable.stackTraceToString()
            setTextColor(ContextCompat.getColor(this@MainActivity, R.color.holo_red_dark))
        }
    }

    fun initRecycleview() {
        mAdpater.testAgainn = {
            testAll = false
            loadAllJob?.cancel()
            mAdpater.list.clear()
            mAdpater.notifyDataSetChanged()
            lifecycleScope.launch {
                viewModel._apiCallStatus.emit(0 to Result.Loading)
            }
            lifecycleScope.launch(dispatchers) {
                listUseCase.get(it).second.invoke()
            }
        }
        binding.recyclerView.adapter = mAdpater
        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecycleview()

        checkUserData()

        val spinnerArrayAdapter: ArrayAdapter<*> = ArrayAdapter<Any?>(
            this,
            R.layout.simple_spinner_dropdown_item,
            listUseCase.map { it.first },
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
                items(),
            )
            mAdpater.notifyDataSetChanged()

            loadAllJob = lifecycleScope.launch(Dispatchers.Default) {
                listUseCase.forEach {
                    launch { it.second.invoke() }
                }
            }
        }

        observeFlows()

        binding.result.movementMethod = ScrollingMovementMethod()
    }

    private fun observeFlows() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiMessage.collect {
                        when (it) {
                            UiMessage.RefreshUserInfo -> checkUserData()
                        }
                    }
                }

                launch {
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
            }
        }
    }

    private fun checkUserData() {
        lifecycleScope.launch {
            val text = koinGet<IUserInfoManager>().getUser()?.let {
                // update UI
                "Logged in as ${it.email}"
            } ?: "Not logged in"

            binding.loginStatus.text = text
        }
    }
}
