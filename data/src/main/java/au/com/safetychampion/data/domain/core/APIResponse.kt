package au.com.safetychampion.data.domain.core

import au.com.safetychampion.data.data.handleAPIError
import au.com.safetychampion.data.util.extension.isNullOrEmpty
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

inline fun <reified T> APIResponse.toItem(
    objName: String? = null
): Result<T> {
    return when (this.success) {
        true -> {
            if (this.result.isNullOrEmpty()) {
                Result.Error(SCError.EmptyResult)
            } else {
                val mObjName = objName ?: when {
                    result!!.has("item") -> "item"
                    result.has("items") -> "items"
                    else -> null
                }
                Result.Success(
                    if (mObjName.isNullOrEmpty()) {
                        result!!.parseObject()
                    } else {
                        result!![mObjName].parseObject()
                    }
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
