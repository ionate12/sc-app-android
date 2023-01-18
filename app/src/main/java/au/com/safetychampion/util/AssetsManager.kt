package au.com.safetychampion.util

import android.content.Context
import android.content.res.AssetManager
import android.net.Uri
import au.com.safetychampion.data.domain.Attachment
import au.com.safetychampion.data.domain.models.TaskAssignStatusItem
import au.com.safetychampion.data.domain.models.action.network.ActionPL
import au.com.safetychampion.data.domain.models.task.Task
import au.com.safetychampion.data.util.extension.itemOrNull
import au.com.safetychampion.data.util.extension.listOrEmpty

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

    fun getNewAction(): ActionPL {
        return context
            .assets
            .readAssetsFile("new_action")
            .itemOrNull<ActionPL>()!!
    }

    fun getAttachment(): List<Attachment> {
        val uri = Uri.parse("android.resource://au.com.safetychampion/drawable/image")
        return listOf(
            Attachment(file = uri, displayName = "Flowers Image", partName = "0", type = "jpg")
        )
    }

    fun getActionId(): String = "63b5622b97f7ee1e8d3d639a"

    fun getEditAction(): ActionPL {
        return context
            .assets
            .readAssetsFile("edit_action_")
            .itemOrNull<ActionPL>()!!
    }
}
