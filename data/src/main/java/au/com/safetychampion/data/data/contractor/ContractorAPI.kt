package au.com.safetychampion.data.data.contractor

import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.core.APIResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ContractorAPI {
    @GET("contractors/{contractorId}/fetch")
    suspend fun fetch(
        @Path("contractorId") contractorId: String?
    ): APIResponse

    @POST("contractors/list")
    suspend fun list(@Body body: BasePL = BasePL.empty()): APIResponse

    @POST("contractors/list/linkedTasks")
    suspend fun linkedTasks(
        @Body body: BasePL = BasePL.empty()
    ): APIResponse
}
