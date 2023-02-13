package au.com.safetychampion.util

import android.content.Context
import android.content.res.AssetManager
import au.com.safetychampion.data.domain.models.TaskAssignStatusItem
import au.com.safetychampion.data.domain.models.action.ActionTask
import au.com.safetychampion.data.domain.models.action.network.ActionPL
import au.com.safetychampion.data.domain.models.action.network.PendingActionPL
import au.com.safetychampion.data.domain.models.chemical.ChemicalTask
import au.com.safetychampion.data.domain.models.crisk.CriskArchivePayload
import au.com.safetychampion.data.domain.models.task.Task
import au.com.safetychampion.data.util.extension.itemOrNull
import au.com.safetychampion.data.util.extension.listOrEmpty

private fun AssetManager.readAssetsFile(fileName: String): String = open(fileName).bufferedReader().use { it.readText() }

class AssetsManager(private val context: Context) {
    suspend fun getSampleTask(): Task {
        return context
            .assets
            .readAssetsFile("task")
            .itemOrNull<Task>()!!
    }

    suspend fun getListSampleTask(): List<Task> {
        return context
            .assets
            .readAssetsFile("tasks")
            .listOrEmpty()
    }

    suspend fun getSampleTaskAssignStatusItem(): TaskAssignStatusItem {
        return context
            .assets
            .readAssetsFile("task_assign_status_item")
            .itemOrNull<TaskAssignStatusItem>()!!
    }

    suspend fun getNewAction(): ActionPL {
        return context
            .assets
            .readAssetsFile("new_action")
            .itemOrNull<ActionPL>()!!
    }

    suspend fun getActionId(): String = "63b5622b97f7ee1e8d3d639a"

    suspend fun getEditAction(): ActionPL {
        return context
            .assets
            .readAssetsFile("edit_action_")
            .itemOrNull<ActionPL>()!!
    }

    suspend fun getActionTask(): ActionTask {
        return context
            .assets
            .readAssetsFile("signoff_action")
            .itemOrNull<ActionTask>()!!
    }

    suspend fun getPendingActionPL(): MutableList<PendingActionPL> {
        return mutableListOf(
            PendingActionPL(
                action = getNewAction()
            )
        )
    }

    suspend fun getChemicalTask(): ChemicalTask {
        return context
            .assets
            .readAssetsFile("chemical_task")
            .itemOrNull<ChemicalTask>()!!
    }

    suspend fun getCriskArchivePL(): CriskArchivePayload {
        return CriskArchivePayload(notes = "Test")
    }
}
