package au.com.safetychampion.data.data

import au.com.safetychampion.data.domain.core.* // ktlint-disable no-wildcard-imports
import au.com.safetychampion.data.network.APIResponse
import au.com.safetychampion.utils.itemOrNull
import au.com.safetychampion.utils.listOrEmpty

abstract class BaseRepository {

    /**
     * Invoke the specified suspend function block, and parses the result (result object in APIResponse) as List<[T]>
     * @param call A suspend function returns APIResponse
     * @see toItems
     */

    suspend inline fun <reified T> callAsList(crossinline call: suspend () -> APIResponse): Result<List<T>> {
        return try {
            call.invoke().toItems()
        } catch (e: Exception) {
            handleRetrofitException(e)
        }
    }

    /**
     * Invoke the specified suspend function block, and parses the result (result object in APIResponse) as [T]
     * @param call A suspend function returns APIResponse
     * @param responseObjName the object name of json object, default value is "item"
     * @see toItems
     */

    suspend inline fun <reified T> call(
        responseObjName: String = "item",
        crossinline call: suspend () -> APIResponse
    ): Result<T> {
        return try {
            call.invoke().toItem()
        } catch (e: Exception) {
            handleRetrofitException(e)
        }
    }

    inline fun <reified T> APIResponse.toItem(responseObjName: String = "item"): Result<T> {
        return when (this.success) {
            true -> {
                if (this.result == null) {
                    Result.Error(SCError.EmptyResult)
                } else {
                    Result.Success(result[responseObjName].itemOrNull())
                }
            }
            false -> {
                return Result.Error(
                    handleAPIError(error)
                )
            }
        }
    }

    inline fun <reified T> APIResponse.toItems(): Result<List<T>> {
        return when (success) {
            true -> {
                if (result == null) {
                    Result.Error(SCError.EmptyResult)
                } else {
                    Result.Success(result["items"].listOrEmpty())
                }
            }
            false -> {
                return Result.Error(
                    handleAPIError(error)
                )
            }
        }
    }

    fun handleAPIError(err: APIResponse.APIError?): SCError {
        return when {
            err?.code?.contains("authorization_token_expired") == true -> SCError.LoginTokenExpired()
            else -> SCError.Failure(err?.message ?: listOf("Unknown Reason"))
        }
    }

    fun handleRetrofitException(e: Exception): Result.Error {
        return Result.Error(SCError.Failure(listOf(e.message!!)))
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
