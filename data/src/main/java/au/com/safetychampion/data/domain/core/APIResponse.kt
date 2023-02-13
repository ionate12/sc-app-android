package au.com.safetychampion.data.domain.core

import au.com.safetychampion.data.data.handleAPIError
import au.com.safetychampion.data.util.extension.isNullOrEmpty
import au.com.safetychampion.data.util.extension.parseList
import au.com.safetychampion.data.util.extension.parseObject
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

suspend inline fun <reified T> APIResponse.toItem(
    responseObjName: String = "item"
): Result<T> {
    return when (this.success) {
        true -> {
            if (this.result.isNullOrEmpty()) {
                Result.Error(SCError.EmptyResult)
            } else {
                Result.Success(result!![responseObjName].parseObject())
            }
        }
        false -> {
            return Result.Error(
                handleAPIError(error)
            )
        }
    }
}

suspend inline fun <reified T> APIResponse.toItems(objName: String = "items"): Result<List<T>> {
    return when (success) {
        true -> {
            if (result.isNullOrEmpty()) {
                Result.Error(SCError.EmptyResult)
            } else {
                Result.Success(result!![objName].parseList())
            }
        }
        false -> {
            return Result.Error(
                handleAPIError(error)
            )
        }
    }
}
