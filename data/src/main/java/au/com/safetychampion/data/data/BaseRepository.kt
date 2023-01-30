package au.com.safetychampion.data.data

import au.com.safetychampion.data.data.api.NetworkAPI
import au.com.safetychampion.data.data.api.RestAPI
import au.com.safetychampion.data.data.local.IStorable
import au.com.safetychampion.data.data.local.ISyncable
import au.com.safetychampion.data.data.local.RoomDataSource
import au.com.safetychampion.data.domain.core.APIResponse
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.core.SCError
import au.com.safetychampion.data.domain.core.flatMap
import au.com.safetychampion.data.domain.core.flatMapError
import au.com.safetychampion.data.domain.core.toItem
import au.com.safetychampion.data.domain.core.toItems
import au.com.safetychampion.data.domain.manager.IDispatchers
import au.com.safetychampion.data.domain.manager.IFileManager
import au.com.safetychampion.data.domain.manager.IGsonManager
import au.com.safetychampion.data.domain.manager.INetworkManager
import au.com.safetychampion.data.domain.toMultipartBody
import au.com.safetychampion.data.util.extension.listOrEmpty
import au.com.safetychampion.util.koinGet
import au.com.safetychampion.util.koinInject
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response

abstract class BaseRepository {

    val networkManager: INetworkManager by koinInject()
    protected val fileContentManager: IFileManager by koinInject()
    private val restAPI: RestAPI by koinInject()
    private val dispatchers: IDispatchers by koinInject()
    private val roomDts: RoomDataSource by koinInject()
    val gson by lazy { koinGet<IGsonManager>().gson }

    /**
     * Invoke the specified suspend function block, and parses the result (result object in APIResponse) as List<[T]>
     * @param call A suspend function returns APIResponse
     * @see toItems
     */

    suspend inline fun <reified T> apiCallAsList(
        crossinline call: suspend () -> APIResponse
    ): Result<List<T>> {
        return try {
            if (!networkManager.isOnline()) {
                return Result.Error(SCError.NoNetwork)
            }
            call.invoke().toItems()
        } catch (e: Exception) {
            handleRetrofitException(e)
        }
    }

    internal suspend inline fun <reified T> NetworkAPI.callAsList(): Result<List<T>> {
        return internalCall().flatMap { it.toItems() }
    }

    internal suspend inline fun <reified T> NetworkAPI.call(
        objName: String = "item"
    ): Result<T> {
        return internalCall().flatMap { it.toItem(objName) }
    }

    /**
     * Introduce cache strategy:
     *   - IStorable: `instance of IStorable` data will automatically stored to Storable Table after a successful API Call.
     *              If nw is offline, data will be cached from Storable Table if exists or is valid.
     *
     *   - ISyncable: if nw is offline, `instance of ISyncable` will automatically store to Syncable Table.
     *              Which later can be synchronized when nw is back online
     */
    private suspend fun NetworkAPI.internalCall(): Result<APIResponse> {
        fun onSuccess(res: APIResponse): Result.Success<APIResponse> {
            // Store data if instance of IStorable
            if (res.success && res.result != null && this is IStorable) {
                roomDts.insertStorable(customKey() ?: path, res.result)
            }
            return Result.Success(res)
        }

        if (!networkManager.isOnline()) {
            // Get Data if nw is offline and is instance of IStorable
            if (this is IStorable) {
                roomDts.getStorable(customKey() ?: path)?.let {
                    return Result.Success(it.toAPIResponse(), offline = true)
                }
            }
            if (this is ISyncable && this is NetworkAPI.PostMultiParts) {
                val key = customKey() ?: path
                roomDts.insertSyncable(key, data = body.toJsonElement(gson).asJsonObject)
                return Result.Error(SCError.SyncableStored(key))
            }
            return Result.Error(SCError.NoNetwork)
        }

        return try {
            when (this) {
                is NetworkAPI.Get -> {
                    onSuccess(restAPI.get(this.path))
                }
                is NetworkAPI.Post -> {
                    onSuccess(restAPI.post(path, body))
                }
                is NetworkAPI.PostMultiParts -> {
                    val parts = mutableListOf(
                        this.body.toRequestBody()
                            .let { MultipartBody.Part.createFormData("json", null, it) }
                    )
                    this.attachment.toMultipartBody(fileContentManager).let { parts.addAll(it) }
                    onSuccess(restAPI.postMultipart(this.path, parts))
                }
                else -> TODO("Compiler error if remove this...")
            }
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
            if (!networkManager.isOnline()) {
                return Result.Error(SCError.NoNetwork)
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

    suspend inline fun <reified T> remoteOrLocalOrError(
        crossinline remote: suspend () -> APIResponse,
        crossinline local: suspend (SCError) -> T?
    ): Result<T> {
        return apiCall<T>(
            call = remote
        ).flatMapError {
            if (it is SCError.NoNetwork) {
                local.invoke(it)?.let { Result.Success(it) }
            } else {
                Result.Error(it)
            }
        }
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
        ).flatMapError {
            if (it is SCError.NoNetwork) {
                local.invoke(it)?.let { Result.Success(it) }
            } else {
                null
            }
        }
    }

    /**
     * @see remoteOrLocalOrError
     */

    private suspend inline fun <reified T> Response<APIResponse>.toItems(): Result<List<T>> {
        errorBody()?.let {
            return handleErrorBody(it)
        }
        body()?.let {
            return when (it.success) {
                true -> {
                    val response = body()
                    if (response?.result == null) {
                        Result.Error(SCError.EmptyResult)
                    } else {
                        if (T::class == Unit::class) {
                            Result.Success(Unit)
                        }
                        Result.Success(response.result["items"].listOrEmpty())
                    }
                }
                false -> Result.Error(
                    handleAPIError(it.error)
                )
            }
        }

        return Result.Error(SCError.Unknown)
    }
}

fun handleRetrofitException(e: Exception): Result.Error {
    return Result.Error(SCError.Failure(listOf(e.message ?: "")))
}

// TODO: Check how to handle errorBody from retrofit 2
fun handleErrorBody(err: ResponseBody): Result.Error {
    return Result.Error(SCError.Unknown)
}

fun handleAPIError(err: APIResponse.APIError?): SCError {
    return when {
        err?.code?.contains("authorization_token_expired") == true -> SCError.LoginTokenExpired
        else -> SCError.Failure(err?.message ?: listOf("Unknown Reason"))
    }
}
