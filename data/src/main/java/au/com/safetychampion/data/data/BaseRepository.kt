package au.com.safetychampion.data.data

import au.com.safetychampion.data.network.APIResponse
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.core.SCError
import au.com.safetychampion.data.gsonadapter.GsonUtil
import au.com.safetychampion.data.gsonadapter.gson
import com.google.gson.reflect.TypeToken

abstract class BaseRepository {
    fun <T> Result<APIResponse>.map(resultLabel: String = "item"): Result<T> {
        val type = object : TypeToken<T>() {}.type
        return when(this) {
            is Result.Success -> {
                if (this.data.success) {
                    this.data.result?.let {
                        Result.Success(gson().fromJson(it[resultLabel], type))
                    } ?: Result.Error(SCError.EmptyResult)
                } else {
                    Result.Error(this.data.error?.let { SCError.fromApiError(it) } ?: SCError.Unknown)
                }
            }
            else -> {}
        }
    }
}

interface BaseSignoffPayload {
}

data class ActionSignoffPayload(val data: Any): BaseSignoffPayload

abstract class SignoffUseCase<T: BaseSignoffPayload> {
//    val offlineRepo
//    val repo: BaseRepo
    val isOnline = true
    val hasOfflineTask = false
    operator suspend fun invoke(pl: T): Result<Any> {
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

class ActionSignoffUseCase: SignoffUseCase<ActionSignoffPayload>() {
    override suspend fun toServer(): Result<Any> {
        TODO("Not yet implemented")
    }
}