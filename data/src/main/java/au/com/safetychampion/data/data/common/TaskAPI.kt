package au.com.safetychampion.data.data.common

import au.com.safetychampion.data.domain.core.APIResponse
import au.com.safetychampion.data.domain.models.action.payload.ActiveTaskPL
import au.com.safetychampion.data.domain.models.action.payload.AssignTaskStatusPL
import au.com.safetychampion.data.domain.models.action.payload.AssignTaskStatusManyPL
import retrofit2.http.Body
import retrofit2.http.POST

interface TaskAPI {
    @POST("tasks/list/active")
    suspend fun active(
        @Body body: ActiveTaskPL
    ): APIResponse

    @POST("tasks/assign/status")
    suspend fun assignTaskStatus(
        @Body body: AssignTaskStatusPL
    ): APIResponse

    @POST("tasks/assign/status")
    suspend fun assignTaskStatusMany(
        @Body body: AssignTaskStatusManyPL
    ): APIResponse

    @POST("tasks/assign")
    suspend fun assignTask(
        @Body body: AssignTaskStatusPL?
    ): APIResponse

    @POST("tasks/unassign")
    suspend fun unassignTask(
        @Body body: AssignTaskStatusPL?
    ): APIResponse
}
