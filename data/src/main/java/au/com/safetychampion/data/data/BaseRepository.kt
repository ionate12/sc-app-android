package au.com.safetychampion.data.data

import au.com.safetychampion.data.domain.core.* // ktlint-disable no-wildcard-imports
import au.com.safetychampion.data.network.APIResponse

abstract class BaseRepository : BaseRepositoryExtensions {

    /**
     * Invoke the specified suspend function block, and parses the result (result object in APIResponse) as [T]
     * @param call
     * Any suspend function returns APIResponse
     * @param tClass
     * the Java class of T
     * @see get
     */
    suspend fun <T> call(call: suspend () -> APIResponse, tClass: Class<T>): Result<T> {
        return when (val result: Result<T> = call.invoke().get(tClass)) {
            is Result.Success -> result
            is Result.Error -> handleError(result.err)
        }
    }

    /**
     * Invoke the specified suspend function block, and parses the result (result object in APIResponse) as List<[T]>
     * @param call
     * A suspend function returns APIResponse
     * @param tClass
     * the Java class of T
     * @see getAsList
     */
    suspend fun <T> callAsList(call: suspend () -> APIResponse, tClass: Class<T>): Result<List<T>> {
        return when (val result: Result<List<T>> = call.invoke().getAsList(tClass)) {
            is Result.Success -> result
            is Result.Error -> handleError(result.err)
        }
    }

    fun handleError(err: SCError): Result.Error {
        return Result.Error(err)
    }
}

interface BaseSignoffPayload

data class ActionSignoffPayload(val data: Any) : BaseSignoffPayload

abstract class SignoffUseCase<T : BaseSignoffPayload> {
    //    val offlineRepo
//    val repo: BaseRepo
    val isOnline = true
    val hasOfflineTask = false
    suspend operator fun invoke(pl: T): Result<Any> {
        return when {
            hasOfflineTask -> overWriteOffline()
            isOnline -> toServer()
            else -> toOffline()
        }
    }
    abstract suspend fun toServer(): Result<Any>
    private suspend fun toOffline(): Result<Any> = Result.Success("")
    private suspend fun overWriteOffline(): Result<Any> = Result.Success("")
}

class ActionSignoffUseCase : SignoffUseCase<ActionSignoffPayload>() {
    override suspend fun toServer(): Result<Any> {
        TODO("Not yet implemented")
    }
}
