package au.com.safetychampion.data.visitor.data.remote

import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.visitor.domain.models.* // ktlint-disable no-wildcard-imports

/**
 * Created by Minh Khoi MAI on 8/12/20.
 */

internal interface IVisitorRemoteRepository {

    suspend fun token(orgId: String, siteId: String, pin: String?): Result<VisitorToken> // right now is a token String

    suspend fun siteFetch(payload: VisitorPayload.SiteFetch): Result<VisitorSite>

    suspend fun formFetch(payload: VisitorPayload.FormFetch): Result<VisitorForm>

    suspend fun evidencesFetch(payload: VisitorPayload.EvidencesFetch): Result<List<VisitorEvidence>>

    suspend fun arrive(payload: VisitorPayload.Arrive): Result<VisitorEvidence>

    suspend fun leave(payload: VisitorPayload.Leave): Result<VisitorEvidence>

    suspend fun evidenceFetch(payload: VisitorPayload.EvidencesFetch): Result<List<VisitorEvidence>>
}
