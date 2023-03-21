package au.com.safetychampion.util

import android.content.Context
import android.content.res.AssetManager
import au.com.safetychampion.data.domain.models.action.ActionTask
import au.com.safetychampion.data.domain.models.action.network.ActionNewPL
import au.com.safetychampion.data.domain.models.action.network.PendingActionPL
import au.com.safetychampion.data.domain.models.chemical.ChemicalTask
import au.com.safetychampion.data.domain.models.contractor.ContractorLinkedTaskPL
import au.com.safetychampion.data.domain.models.crisk.CriskArchivePayload
import au.com.safetychampion.data.domain.models.document.DocumentSignoff
import au.com.safetychampion.data.domain.models.incidents.IncidentNewPL
import au.com.safetychampion.data.domain.models.task.Task
import au.com.safetychampion.data.domain.models.task.TaskAssignStatusItem
import au.com.safetychampion.data.util.extension.parseObject

private fun AssetManager.readAssetsFile(fileName: String): String = open(fileName).bufferedReader().use { it.readText() }

class AssetsManager(private val context: Context) {
    fun getSampleTask(): Task {
        return context
            .assets
            .readAssetsFile("task")
            .parseObject<Task>()!!
    }

    fun getListSampleTask(): List<Task> {
        return context
            .assets
            .readAssetsFile("tasks")
            .parseObject<List<Task>>()!!
    }

    fun getSampleTaskAssignStatusItem(): TaskAssignStatusItem {
        return context
            .assets
            .readAssetsFile("task_assign_status_item")
            .parseObject<TaskAssignStatusItem>()!!
    }

    fun getNewAction(): ActionNewPL {
        return context
            .assets
            .readAssetsFile("new_action")
            .parseObject<ActionNewPL>()!!
    }

    fun getActionId(): String = "63b5622b97f7ee1e8d3d639a"

//     fun getEditAction(): ActionPL {
//        return context
//            .assets
//            .readAssetsFile("edit_action_")
//            .parseObject<ActionPL>()!!
//    }

    fun getActionTask(): ActionTask {
        return context
            .assets
            .readAssetsFile("signoff_action")
            .parseObject<ActionTask>()!!
    }

    fun getPendingActionPL(): MutableList<PendingActionPL> {
        return mutableListOf(
            PendingActionPL(
                action = getNewAction()
            )
        )
    }

    fun getChemicalTask(): ChemicalTask {
        return context
            .assets
            .readAssetsFile("chemical_task")
            .parseObject<ChemicalTask>()!!
    }

    fun getCriskArchivePL(): CriskArchivePayload {
        return CriskArchivePayload(notes = "Test")
    }

    fun getLinkedTaskPayload(): ContractorLinkedTaskPL = ContractorLinkedTaskPL(ContractorLinkedTaskPL.Id("5efbedcac6bac31619e1221e"))

    fun getSignoffChemical(): DocumentSignoff {
        return context
            .assets
            .readAssetsFile("document_signoff")
            .parseObject<DocumentSignoff>()!!
    }

    fun getNewIncident(): IncidentNewPL {
        return context
            .assets
            .readAssetsFile("incident_new")
            .parseObject()!!
    }
}
