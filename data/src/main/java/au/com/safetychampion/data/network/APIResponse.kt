package au.com.safetychampion.data.network

import au.com.safetychampion.data.data.handleAPIError
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.core.SCError
import au.com.safetychampion.util.itemOrNull
import au.com.safetychampion.util.listOrEmpty
import com.google.gson.JsonObject

data class APIResponse(
    val success: Boolean,
    val result: JsonObject?,
    val error: APIError?
) {
    data class APIError(
        val code: List<String>,
        val message: List<String>
    )
}

inline fun <reified T> APIResponse.toItem(
    responseObjName: String = "item"
): Result<T> {
    return when (this.success) {
        true -> {
            if (this.result == null) {
                Result.Error(SCError.EmptyResult)
            } else {
                if (T::class == Unit::class) {
                    return Result.Success(null)
                }
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
