package au.com.safetychampion.util

import au.com.safetychampion.data.domain.manager.IOfflineConverter
import au.com.safetychampion.data.domain.usecase.action.ActionSignOffParam
import au.com.safetychampion.data.domain.usecase.action.OfflineTask
import au.com.safetychampion.data.domain.usecase.action.OfflineTaskInfo

class OfflineTaskManager : IOfflineConverter {
    override fun toOfflineTask(taskData: OfflineTaskInfo): OfflineTask {
        return when (taskData) {
            is ActionSignOffParam -> OfflineTask(taskData.offlineTitle, "SSSS")
            else -> TODO()
        }
    }
}
