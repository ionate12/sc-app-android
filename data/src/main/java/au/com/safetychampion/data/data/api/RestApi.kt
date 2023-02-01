package au.com.safetychampion.data.data.api

import au.com.safetychampion.data.domain.Attachment
import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.core.APIResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface RestApi {

    @GET("{path}")
    suspend fun get(@Path(value = "path", encoded = true) path: String): APIResponse

    @POST("{path}")
    suspend fun post(@Path(value = "path", encoded = true) path: String, @Body body: BasePL?): APIResponse

    @Multipart
    @POST("{path}")
    suspend fun postMultipart(@Path(value = "path", encoded = true) path: String, @Part parts: List<MultipartBody.Part>?): APIResponse
}

sealed class NetworkAPI(val path: String) {
    sealed class Get(path: String) : NetworkAPI(path)
    sealed class Post(path: String, val body: BasePL?) : NetworkAPI(path)
    sealed class PostMultiParts(path: String, val body: BasePL, val attachment: List<Attachment>?) : NetworkAPI(path)
}
