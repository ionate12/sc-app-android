package au.com.safetychampion.data.data

import au.com.safetychampion.data.domain.core.* // ktlint-disable no-wildcard-imports
import au.com.safetychampion.data.domain.manager.IFileManager
import au.com.safetychampion.data.domain.manager.INetworkManager
import au.com.safetychampion.util.koinInject

abstract class BaseRepository {

    val networkManager = koinInject<INetworkManager>()
    protected val fileContentManager: IFileManager by koinInject()

    /**
     * Invoke the specified suspend function block, and parses the result (result object in APIResponse) as List<[T]>
     * @param call A suspend function returns APIResponse
     * @see toItems
     */

    suspend inline fun <reified T> apiCallAsList(
        crossinline call: suspend () -> APIResponse
    ): Result<List<T>> {
        return try {
            if (!networkManager.value.isNetworkAvailable()) {
                return Result.Error(SCError.NoNetwork())
            }
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

    suspend inline fun <reified T> apiCall(
        responseObjName: String = "item",
        crossinline call: suspend () -> APIResponse
    ): Result<T> {
        return try {
            if (!networkManager.value.isNetworkAvailable()) {
                return Result.Error(SCError.NoNetwork())
            }
            call.invoke().toItem(responseObjName)
        } catch (e: Exception) {
            handleRetrofitException(e)
        }
    }

    /**
     * Trying to fetch data from the remote datasource, with [remote] as the parameter of [apiCall] function,
     * If [apiCall] is failed, and it returns a [SCError.NoNetwork] wrapped in [Result.Error], then invokes the [local] as the fallback.
     * @param remote Remote datasource: an API request returns APIResponse
     * @param local Local datasource: using as a fallback and only be invoked if we have a SCError.NoNetwork from [apiCall]
     * @return [Result.Success] if [remote] is [Result.Success] || [remote] is [Result.Error] but [local].invokes() != null,
     * [Result.Error] otherwise
     * @see flatMapError
     */

    suspend inline fun <reified T : Any> remoteOrLocalOrError(
        crossinline remote: suspend () -> APIResponse,
        crossinline local: suspend (SCError) -> T?
    ): Result<T> {
        return apiCall<T>(
            call = remote
        ).flatMapError(
            expectedError = SCError.NoNetwork::class,
            action = { scError ->
                local.invoke(scError)?.let { Result.Success(it) } ?: Result.Error(scError)
            }
        )
    }

    /**
     * @see remoteOrLocalOrError
     */

    suspend inline fun <reified T : Any> remoteOrLocalOrErrorAsList(
        crossinline remote: suspend () -> APIResponse,
        crossinline local: suspend (SCError) -> List<T>?
    ): Result<List<T>> {
        return apiCallAsList<T>(
            call = remote
        ).flatMapError(
            expectedError = SCError.NoNetwork::class,
            action = { scError ->
                local.invoke(scError)?.let { Result.Success(it) } ?: Result.Error(scError)
            }
        )
    }
}

fun handleRetrofitException(e: Exception): Result.Error {
    return Result.Error(SCError.Failure(listOf(e.message ?: "")))
}

fun handleAPIError(err: APIResponse.APIError?): SCError {
    return when {
        err?.code?.contains("authorization_token_expired") == true -> SCError.LoginTokenExpired()
        else -> SCError.Failure(err?.message ?: listOf("Unknown Reason"))
    }
}
