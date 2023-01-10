package au.com.safetychampion.data.domain.manager

import au.com.safetychampion.data.domain.usecase.action.OfflineTask
import au.com.safetychampion.data.domain.usecase.action.OfflineTaskInfo

interface IOfflineConverter {
    fun toOfflineTask(taskData: OfflineTaskInfo): OfflineTask
}
