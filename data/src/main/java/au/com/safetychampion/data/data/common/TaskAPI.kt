package au.com.safetychampion.data.data.common

import au.com.safetychampion.data.domain.core.APIResponse
import au.com.safetychampion.data.domain.models.action.payload.ActiveTask
import au.com.safetychampion.data.domain.models.action.payload.AssignTaskStatusMany
import au.com.safetychampion.data.domain.models.action.payload.AssignTaskStatus
import retrofit2.http.Body
import retrofit2.http.POST

interface TaskAPI {
    @POST("tasks/list/active")
    suspend fun active(
        @Body body: ActiveTask
    ): APIResponse

    @POST("tasks/assign/status")
    suspend fun assignTaskStatus(
        @Body body: AssignTaskStatus
    ): APIResponse

    @POST("tasks/assign/status")
    suspend fun assignTaskStatusMany(
        @Body body: AssignTaskStatusMany
    ): APIResponse

    @POST("tasks/assign")
    suspend fun assignTask(
        @Body body: AssignTaskStatus?
    ): APIResponse

    @POST("tasks/unassign")
    suspend fun unassignTask(
        @Body body: AssignTaskStatus?
    ): APIResponse
}
