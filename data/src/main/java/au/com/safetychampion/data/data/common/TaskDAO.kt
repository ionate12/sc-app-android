package au.com.safetychampion.data.data.common

import au.com.safetychampion.data.domain.usecase.action.OfflineTask
import timber.log.Timber

interface TaskDAO {
    fun insertOfflineTask(offTask: OfflineTask)
    fun getOfflineTask(taskId: String?): OfflineTask?
}

class FakeDAO : TaskDAO {
    override fun insertOfflineTask(offTask: OfflineTask) {
        Timber.tag("FakeDAO").d("insertOfflineTask")
    }

    override fun getOfflineTask(taskId: String?): OfflineTask? {
        Timber.tag("FakeDAO").d("getOfflineTask")
        return OfflineTask(title = "dsdsd")
    }
}
