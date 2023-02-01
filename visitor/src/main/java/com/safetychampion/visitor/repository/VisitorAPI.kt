package au.com.safetychampion.scmobile.visitorModule.repository

import au.com.safetychampion.scmobile.apiservice.APIResponses.ResponseEnv
import au.com.safetychampion.scmobile.constantValues.Constants
import au.com.safetychampion.scmobile.visitorModule.models.VisitorPayload
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 * Created by Minh Khoi MAI on 18/12/20.
 */

interface VisitorAPI {
    companion object {
        const val BODY_JSON = "json"
    }

    @Headers(Constants.ContentTypeHeader)
    @POST("/visitors/visit/token")
    fun token(@Body body: VisitorPayload.Token): Single<ResponseEnv>  // Body { org, site, pin }

    @Headers(Constants.ContentTypeHeader)
    @POST("/visitors/linkaccess/site/fetch")
    fun siteFetch(@Body body: VisitorPayload.SiteFetch): Single<ResponseEnv> //Body { token }

    @Headers(Constants.ContentTypeHeader)
    @POST("/visitors/linkaccess/form/fetch")
    fun formFetch(@Body body: VisitorPayload.FormFetch): Single<ResponseEnv> //Body { token, id }

    @Headers(Constants.ContentTypeHeader)
    @POST("/visitors/linkaccess/visit/arrive")
    fun arrive(@Body body: VisitorPayload.Arrive): Single<ResponseEnv> //Body { token, arrive, visitor, tz}

    @Headers(Constants.ContentTypeHeader)
    @POST("/visitors/linkaccess/visit/leave")
    fun leave(@Body body: VisitorPayload.Leave): Single<ResponseEnv> //Body { token, leave, tz}

    @Headers(Constants.ContentTypeHeader)
    @POST("/visitors/linkaccess/visit/fetch")
    fun visitFetch(@Body body: VisitorPayload.VisitFetch): Single<ResponseEnv>
}
