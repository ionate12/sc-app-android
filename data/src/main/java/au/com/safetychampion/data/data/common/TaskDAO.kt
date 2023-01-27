package au.com.safetychampion.data.data.common

import au.com.safetychampion.data.domain.models.GHSCode
import au.com.safetychampion.data.domain.models.chemical.Chemical
import au.com.safetychampion.data.domain.usecase.action.OfflineTask
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import timber.log.Timber
interface MasterDAO {
    fun insertToDB(vararg list: Any)
    fun getChemicalList(): Flow<List<Chemical>>
    fun getGHSCode(): Flow<GHSCode>
}

interface TaskDAO {
    fun insertOfflineTask(offTask: OfflineTask)
    fun getOfflineTask(taskId: String?): OfflineTask?
}

class FakeMasterDAO : MasterDAO {
    override fun insertToDB(vararg list: Any) {
        Timber.d("insertToDB$list")
    }

    override fun getChemicalList(): Flow<List<Chemical>> {
        return emptyFlow()
    }

    override fun getGHSCode(): Flow<GHSCode> {
        return emptyFlow()
    }
}
class FakeDAO : TaskDAO {
    override fun insertOfflineTask(offTask: OfflineTask) {
        Timber.d("inserted OfflineTask")
    }

    override fun getOfflineTask(taskId: String?): OfflineTask? {
        Timber.d("getOfflineTask")
        return OfflineTask(title = "dsdsd")
    }
}
