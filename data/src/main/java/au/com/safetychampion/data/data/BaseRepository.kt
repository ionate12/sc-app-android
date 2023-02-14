package au.com.safetychampion.data.data

import au.com.safetychampion.data.data.api.NetworkAPI
import au.com.safetychampion.data.data.api.RestApi
import au.com.safetychampion.data.data.local.IStorable
import au.com.safetychampion.data.data.local.ISyncable
import au.com.safetychampion.data.data.local.RoomDataSource
import au.com.safetychampion.data.domain.core.* // ktlint-disable no-wildcard-imports
import au.com.safetychampion.data.domain.core.APIResponse
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.core.SCError
import au.com.safetychampion.data.domain.core.flatMap
import au.com.safetychampion.data.domain.core.toItem
import au.com.safetychampion.data.domain.manager.IDispatchers
import au.com.safetychampion.data.domain.manager.IFileManager
import au.com.safetychampion.data.domain.manager.INetworkManager
import au.com.safetychampion.data.domain.toMultipartBody
import au.com.safetychampion.data.util.extension.koinInject
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody

abstract class BaseRepository {
    protected val dispatchers: IDispatchers by koinInject()

    protected val networkManager: INetworkManager by koinInject()

    protected val fileContentManager: IFileManager by koinInject()

    private val restAPI: RestApi by koinInject()

    private val roomDts: RoomDataSource by koinInject()

    internal suspend inline fun <reified T> NetworkAPI.call(objName: String? = null): Result<T> {
        return internalCall().flatMap {
            it.toItem(objName)
        }
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
        val isOffline = !networkManager.isOnline()
        return if (isOffline) {
            handleOffline()
        } else {
            handleOnline()
        }
    }

    private suspend fun NetworkAPI.handleOnline(): Result<APIResponse> =
        withContext(dispatchers.io) {
            val onSuccess: (res: APIResponse) -> Result.Success<APIResponse> = { res ->
                // Store data if instance of IStorable
                if (res.success && res.result != null && this is IStorable) {
                    roomDts.insertStorable(customKey() ?: path, res.result)
                }
                Result.Success(res)
            }
            try {
                when (this@handleOnline) {
                    is NetworkAPI.Get -> {
                        onSuccess(restAPI.get(this@handleOnline.path))
                    }
                    is NetworkAPI.Post -> {
                        onSuccess(restAPI.post(path, body))
                    }
                    is NetworkAPI.PostMultiParts -> {
                        val parts = mutableListOf(
                            this@handleOnline.body.toRequestBody()
                                .let { MultipartBody.Part.createFormData("json", null, it) }
                        )
                        this@handleOnline.attachment.toMultipartBody(fileContentManager)
                            .let { parts.addAll(it) }
                        onSuccess(restAPI.postMultipart(this@handleOnline.path, parts))
                    }
                }
            } catch (e: Exception) {
                handleRetrofitException(e)
            }
        }

    private suspend fun NetworkAPI.handleOffline(): Result<APIResponse> =
        withContext(dispatchers.default) {
            return@withContext when {
                this is IStorable -> {
                    roomDts.getStorable(customKey() ?: path)?.let {
                        Result.Success(it.toAPIResponse(), offline = true)
                    } ?: Result.Error(SCError.NoNetwork)
                }
                this is ISyncable && this is NetworkAPI.PostMultiParts -> {
                    val key = customKey() ?: path
                    roomDts.insertSyncable(key, data = body)
                    Result.Error(SCError.SyncableStored(key))
                }
                else -> Result.Error(SCError.NoNetwork)
            }
        }
}

fun handleRetrofitException(e: Exception): Result.Error {
    return Result.Error(SCError.Failure(listOf(e.message ?: "")))
}

fun handleAPIError(err: APIResponse.APIError?): SCError {
    return when {
        err?.code?.contains("authorization_token_expired") == true -> SCError.LoginTokenExpired
        else -> SCError.Failure(err?.message ?: listOf("Unknown Reason"))
    }
}
