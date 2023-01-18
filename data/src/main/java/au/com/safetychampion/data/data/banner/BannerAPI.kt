package au.com.safetychampion.data.data.banner

import au.com.safetychampion.data.domain.BannerListPayload
import au.com.safetychampion.data.domain.core.APIResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface BannerAPI {
    @POST("dashboardbanner/list")
    suspend fun list(
        @Body body: BannerListPayload = BannerListPayload()
    ): APIResponse
}
