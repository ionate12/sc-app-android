package au.com.safetychampion.data.data

import au.com.safetychampion.data.domain.core.* // ktlint-disable no-wildcard-imports
import au.com.safetychampion.data.domain.extensions.BaseRepositoryExtensions
import au.com.safetychampion.data.network.APIResponse
import java.net.UnknownHostException

abstract class BaseRepository : BaseRepositoryExtensions {

    /**
     * Invoke the specified suspend function block, and parses the result (result object in APIResponse) as [T]
     * @param call
     * A suspend function returns APIResponse
     * @param classOfT
     * the Java class of [T]
     * @param keyItem
     * default value is "item"
     * @see get
     */

    suspend fun <T> call(
        call: suspend () -> APIResponse,
        classOfT: Class<T>,
        keyItem: String = "item"
    ): Result<T> {
        return try {
            call
                .invoke()
                .get(
                    keyItem = keyItem,
                    tClass = classOfT
                )
        } catch (e: UnknownHostException) {
            Result.Error(SCError.NoInternetConnection())
        } catch (e: Exception) {
            Result.Error(SCError.Failure(listOf(e.message!!)))
        }
    }

    /**
     * Invoke the specified suspend function block, and parses the result (result object in APIResponse) as List<[T]>
     * @param call
     * A suspend function returns APIResponse
     * @param tClass
     * the Java class of [T]
     * @see getAsList
     */
    suspend fun <T> callAsList(call: suspend () -> APIResponse, tClass: Class<T>): Result<List<T>> {
        return try {
            call
                .invoke()
                .getAsList(tClass)
        } catch (e: UnknownHostException) {
            Result.Error(SCError.NoInternetConnection())
        } catch (e: Exception) {
            Result.Error(SCError.Failure(listOf(e.message!!)))
        }
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
