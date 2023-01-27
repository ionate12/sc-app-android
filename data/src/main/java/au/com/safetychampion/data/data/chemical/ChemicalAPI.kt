package au.com.safetychampion.data.data.chemical

import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.core.APIResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.* // ktlint-disable no-wildcard-imports

interface ChemicalAPI {
    @POST("/chemicals/list")
    suspend fun listChemicals(
        @Body body: BasePL = BasePL.empty()
    ): APIResponse

    @GET("chemicals/list/ghs/codes")
    suspend fun listCodes(): APIResponse

    @GET("chemicals/{chemId}/fetch")
    suspend fun fetch(
        @Path("chemId") chemId: String?
    ): APIResponse

    @Multipart
    @POST("chemicals/{chemId}/tasks/{taskId}/signoff")
    suspend fun signOff(
        @Path("chemId") moduleId: String?,
        @Path("taskId") taskId: String?,
        @Part("json") body: RequestBody?,
        @Part photos: List<MultipartBody.Part>?
    ): APIResponse
}
