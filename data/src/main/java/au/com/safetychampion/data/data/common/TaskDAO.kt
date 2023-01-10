package au.com.safetychampion.data.data.common

import au.com.safetychampion.data.domain.usecase.action.OfflineTask

interface TaskDAO {
    fun insertOfflineTask(offTask: OfflineTask)
    fun getOfflineTask(taskId: String?): OfflineTask?
}
