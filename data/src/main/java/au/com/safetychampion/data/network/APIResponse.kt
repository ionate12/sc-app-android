package au.com.safetychampion.data.network

import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.core.SCError
import au.com.safetychampion.data.domain.extensions.listOrEmpty
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
