package au.com.safetychampion.data.network

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
