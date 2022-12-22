package au.com.safetychampion.data.util

import au.com.safetychampion.utils.getKoinInstance
import kotlinx.coroutines.CoroutineDispatcher

interface IDispatchers {
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
}

fun dispatchers(): IDispatchers = getKoinInstance()
