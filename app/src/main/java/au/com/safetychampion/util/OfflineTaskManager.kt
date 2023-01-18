package au.com.safetychampion.util

import au.com.safetychampion.data.domain.manager.IOfflineConverter
import au.com.safetychampion.data.domain.models.action.database.ActionSignOffEntity
import au.com.safetychampion.data.domain.usecase.action.OfflineTask
import au.com.safetychampion.data.domain.usecase.action.OfflineTaskInfo

class OfflineTaskManager : IOfflineConverter {
    override fun toOfflineTask(taskData: OfflineTaskInfo): OfflineTask {
        return when (taskData) {
            is ActionSignOffEntity -> OfflineTask(taskData.offlineTitle, "SSSS")
            else -> TODO()
        }
    }

    override fun <T> toObject(offlineTask: OfflineTask?): T? {
        TODO("Not yet implemented")
    }
}
