package au.com.safetychampion.data.data

import au.com.safetychampion.data.domain.core.* // ktlint-disable no-wildcard-imports
import au.com.safetychampion.data.gsonadapter.gson
import au.com.safetychampion.data.network.APIResponse
import com.google.gson.reflect.TypeToken

abstract class BaseRepository {

    /** Gets the result of [APIResponse] as object [T] or List<`T`> wrapped in [Result], with the given key
     * @param responseObjName the object name
     */

    private fun <T> APIResponse.get(responseObjName: String = "item"): Result<T> {
        return if (success) {
            if (result == null) {
                Result.Error(SCError.EmptyResult)
            }

            val type = object : TypeToken<T>() {}.type
            try {
                Result.Success(gson().fromJson(result!![responseObjName], type))
            } catch (ex: Exception) {
                Result.Error(SCError.JsonSyntaxException(ex.message ?: ""))
            }
        } else {
            Result.Error(SCError.Failure(error?.message ?: listOf("Unknown Reason")))
        }
    }

    suspend fun <T> call(call: suspend () -> APIResponse): Result<T> {
        return when (val result = call.invoke().get<T>("item")) {
            is Result.Success -> result
            is Result.Error -> handleError(result.err)
        }
    }

    suspend fun <T> callAsList(call: suspend () -> APIResponse): Result<List<T>> {
        return when (val result = call.invoke().get<List<T>>("items")) {
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
