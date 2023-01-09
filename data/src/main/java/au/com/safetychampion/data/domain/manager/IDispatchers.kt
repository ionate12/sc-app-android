package au.com.safetychampion.data.domain.manager

import kotlinx.coroutines.CoroutineDispatcher

interface IDispatchers {
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
}
