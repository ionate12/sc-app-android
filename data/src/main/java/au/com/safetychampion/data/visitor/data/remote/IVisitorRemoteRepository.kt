package au.com.safetychampion.data.visitor.data.remote

import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.visitor.domain.models.* // ktlint-disable no-wildcard-imports

/**
 * Created by Minh Khoi MAI on 8/12/20.
 */

internal interface IVisitorRemoteRepository {

    suspend fun token(orgId: String, siteId: String, pin: String?): Result<VisitorToken> // right now is a token String

    suspend fun siteFetch(token: String): Result<VisitorSite>

    suspend fun formFetch(token: String, formId: String): Result<VisitorForm>

    suspend fun visitFetch(tokens: List<String>): Result<List<VisitorEvidence>>

    suspend fun arrive(token: String, profile: VisitorProfile, arriveForm: VisitorForm): Result<VisitorEvidence>

    suspend fun leave(token: String, leaveForm: VisitorForm?): Result<VisitorEvidence>

    suspend fun signOut(profile: VisitorProfile, site: VisitorSite): Result<Any>
}
