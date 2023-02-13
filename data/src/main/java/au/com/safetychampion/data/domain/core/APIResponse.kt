package au.com.safetychampion.data.domain.core

import au.com.safetychampion.data.data.handleAPIError
import au.com.safetychampion.data.util.extension.itemOrNull
import au.com.safetychampion.data.util.extension.listOrEmpty
import com.google.gson.JsonObject

interface IResponse {
    val success: Boolean
}

// TODO: Move to data, make it internal.
data class APIResponse(
    override val success: Boolean,
    val result: JsonObject?,
    val error: APIError?
) : IResponse {
    data class APIError(
        val code: List<String>,
        val message: List<String>
    )
}
// data class SyncableResponse(
//    override val success: Boolean,
//    val msg: String,
//    val extras: Map<String, Any>? = null // Temporary set as Map
// ) : IResponse

inline fun <reified T> APIResponse.toItem(
    responseObjName: String? = "item"
): Result<T> {
    return when (this.success) {
        true -> {
            if (this.result == null) {
                Result.Error(SCError.EmptyResult)
            } else {
                if (T::class == Unit::class) {
                    Result.Success(Unit)
                }
                val jElement = responseObjName?.let { result[responseObjName] } ?: result
                Result.Success(
                    jElement.itemOrNull()
                )
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
                if (T::class == Unit::class) {
                    Result.Success(Unit)
                }
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
