package au.com.safetychampion.data.domain.core

import au.com.safetychampion.data.domain.manager.IDispatchers
import au.com.safetychampion.data.util.extension.koinInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

abstract class SuspendableInit {
    private var initJob: Job? = null
    private val dispatchers: IDispatchers by koinInject()

    init {
        initJob = initialize()
    }

    protected suspend fun <T : Any?> didInit(block: suspend () -> T): T {
        initJob?.join()
        return block.invoke()
    }

    private fun initialize(): Job = CoroutineScope(dispatchers.io).launch {
        suspendInit()
    }
    abstract suspend fun suspendInit()
}
