package au.com.safetychampion.data.data.common

import au.com.safetychampion.data.network.APIResponse
import com.google.gson.JsonObject
import retrofit2.http.Body
import retrofit2.http.POST

interface TaskAPI {
    @POST("tasks/list/active")
    suspend fun active(
        @Body body: JsonObject
    ): APIResponse

    @POST("tasks/assign/status")
    suspend fun assignTaskStatus(
        @Body body: JsonObject
    ): APIResponse
}
