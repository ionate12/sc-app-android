package au.com.safetychampion.utils

import android.content.Context
import android.content.res.AssetManager
import au.com.safetychampion.data.domain.models.TaskAssignStatusItem
import au.com.safetychampion.data.domain.models.task.Task
import au.com.safetychampion.util.itemOrNull
import au.com.safetychampion.util.listOrEmpty

private fun AssetManager.readAssetsFile(fileName: String): String = open(fileName).bufferedReader().use { it.readText() }

class AssetsManager(private val context: Context) {
    fun getSampleTask(): Task {
        return context
            .assets
            .readAssetsFile("task")
            .itemOrNull<Task>()!!
    }

    fun getListSampleTask(): List<Task> {
        return context
            .assets
            .readAssetsFile("tasks")
            .listOrEmpty()
    }

    fun getSampleTaskAssignStatusItem(): TaskAssignStatusItem {
        return context
            .assets
            .readAssetsFile("task_assign_status_item")
            .itemOrNull<TaskAssignStatusItem>()!!
    }
}
