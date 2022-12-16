package au.com.safetychampion

import android.content.Context
import android.content.res.AssetManager
import au.com.safetychampion.data.domain.extensions.itemOrNull
import au.com.safetychampion.data.domain.extensions.listOrEmpty
import au.com.safetychampion.data.domain.models.task.Task

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
}
