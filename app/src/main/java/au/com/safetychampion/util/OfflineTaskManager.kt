package au.com.safetychampion.util

import au.com.safetychampion.data.domain.manager.IOfflineConverter
import au.com.safetychampion.data.domain.usecase.action.OfflineTask
import au.com.safetychampion.data.domain.usecase.action.SignoffParams

class OfflineTaskManager : IOfflineConverter {
    override fun toOfflineTask(taskData: SignoffParams): OfflineTask {
        TODO()
//        return when (taskData) {
//            is ActionSignOffEntity -> OfflineTask(taskData.offlineTitle, "SSSS")
//            else -> TODO()
//        }
    }

    override fun <T> toObject(offlineTask: OfflineTask?): T? {
        return null
    }
}
