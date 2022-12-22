package au.com.safetychampion

import android.R
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.databinding.ActivityMainBinding
import au.com.safetychampion.util.asJson
import au.com.safetychampion.utils.AssetsManager
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModel<MainViewModel>()
    private val sampleData = AssetsManager(this)
    private val listUseCase = listOf(
        "Get Active Task (tasks/list/active)" to { viewModel.loadActiveTasks() },
        "Load Active Tasks ReViewPlan (tasks/list/active)" to { viewModel.loadActiveTasksReViewPlan() },
        "Assign Task Status (tasks/assign/status)" to { viewModel.assignTaskStatus(sampleData.getSampleTask()) },
        "Assign Task Status Many (tasks/assign/status)" to { viewModel.assignTaskStatusForMany(sampleData.getListSampleTask()) },
        "Assign Task" to { viewModel.assignTask(ownerTask = sampleData.getSampleTask(), assignTask = sampleData.getSampleTaskAssignStatusItem()) },
        "UnAssign Task" to { viewModel.unAssignTask(ownerTask = sampleData.getSampleTask(), assignTask = sampleData.getSampleTaskAssignStatusItem()) }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
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

        binding.copypayload.setOnClickListener {
            val clip = ClipData.newPlainText("text", viewModel.payload.value)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this, "Copied", Toast.LENGTH_LONG).show()
        }
        binding.api.adapter = (spinnerArrayAdapter)
        binding.test.setOnClickListener {
            listUseCase[binding.api.selectedItemPosition].second.invoke()
        }

        lifecycleScope.launch {
            viewModel.apiCallStatus.collectLatest {
                binding.progress.isVisible = it is Result.Loading
                when {
                    it is Result.Loading -> {
                        binding.status.text = "This is Result.Loading"
                        binding.result.text = ""
                        binding.payload.text = ""
                        binding.count.text = "[]"
                    }
                    it is Result.Error -> {
                        binding.status.text = "This is Result.ERROR"
                        binding.result.text = it.err.toString()
                    }

                    it is Result.Success -> {
                        binding.status.text = "This is Result.Success"
                        binding.result.text = it.data?.asJson()
                        binding.count.text = "[${(it.data as? List<*>)?.size}]"
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.payload.collectLatest {
                binding.payload.text = it
            }
        }

        binding.payload.setMovementMethod(ScrollingMovementMethod())
        binding.result.setMovementMethod(ScrollingMovementMethod())
    }
}
