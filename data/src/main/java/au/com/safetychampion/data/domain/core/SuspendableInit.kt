package au.com.safetychampion.data.domain.core

import au.com.safetychampion.data.domain.manager.IDispatchers
import au.com.safetychampion.data.util.extension.koinInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout

abstract class SuspendableInit {
    private var initJob: Job? = null
    private val dispatchers: IDispatchers by koinInject()
    protected open val initTimeOut = 2000L
    init {
        initJob = initialize()
    }

    protected suspend fun <T : Any?> didInit(
        onTimedOut: (suspend () -> T)? = null,
        block: suspend () -> T
    ): T {
        initJob?.join()
        if (initJob?.isCancelled == true) {
            onTimedOut?.invoke()?.let { return it }
        }
        return block.invoke()
    }

    private fun initialize(): Job = CoroutineScope(dispatchers.io).launch {
        withTimeout(initTimeOut) {
            suspendInit()
        }
    }

    /**
     * This method should be initialized within the initTimeOut
     * @see initTimeOut
     */
    abstract suspend fun suspendInit()
}
