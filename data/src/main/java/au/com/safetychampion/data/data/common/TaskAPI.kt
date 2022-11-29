package au.com.safetychampion.data.data.common

import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.network.APIResponse
import retrofit2.http.POST

interface TaskAPI {

    @POST("tasks/list/active")
    suspend fun active(): Result<APIResponse>

}