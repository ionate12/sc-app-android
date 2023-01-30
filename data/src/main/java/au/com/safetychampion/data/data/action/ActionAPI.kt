package au.com.safetychampion.data.data.action

import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.core.APIResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.* // ktlint-disable no-wildcard-imports

interface ActionAPI {
    @Multipart
    @POST("actions/new")
    suspend fun newAction(
        @Part("json") body: RequestBody?,
        @Part photos: List<MultipartBody.Part>
    ): APIResponse

    @GET("actions/{taskId}/fetch")
    suspend fun fetch(
        @Path("taskId") _id: String?
    ): APIResponse

    @Multipart
    @POST("actions/{taskId}/edit")
    suspend fun editAction(
        @Path("taskId") taskId: String?,
        @Part("json") body: RequestBody?,
        @Part photos: List<MultipartBody.Part>?
    ): APIResponse

    @GET("actions/{taskId}/task")
    suspend fun task(
        @Path("taskId") taskId: String?
    ): APIResponse

    @POST("actions/list")
    suspend fun list(
        @Body body: BasePL?
    ): APIResponse

    @Multipart
    @POST("actions/{actionId}/task/signoff")
    suspend fun signOff(
        @Path("actionId") actionId: String?,
        @Part("json") body: RequestBody?,
        @Part photos: List<MultipartBody.Part>?
    ): APIResponse
}


