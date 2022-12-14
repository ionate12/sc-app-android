package au.com.safetychampion

import android.content.Context
import android.content.res.AssetManager
import au.com.safetychampion.data.domain.core.getData
import au.com.safetychampion.data.domain.extensions.asListT
import au.com.safetychampion.data.domain.extensions.asT
import au.com.safetychampion.data.domain.extensions.jsonArrayOf
import au.com.safetychampion.data.domain.extensions.jsonObjectOf
import au.com.safetychampion.data.domain.models.task.Task

private fun AssetManager.readAssetsFile(fileName: String): String = open(fileName).bufferedReader().use { it.readText() }

class AssertManager(private val context: Context) {
    fun getSampleTask(): Task {
        return jsonObjectOf(
            context.assets.readAssetsFile("task")
        )!!.asT(Task::class.java).getData()!!
    }

    fun getListSampleTask(): List<Task> {
        return jsonArrayOf(
            context.assets.readAssetsFile("tasks")
        )!!.asListT(Task::class.java).getData()!!
    }
}
