package au.com.safetychampion.data.visitor.data.remote // ktlint-disable filename

import au.com.safetychampion.data.data.BaseRepository
import au.com.safetychampion.data.data.api.VisitorApi
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.core.flatMap
import au.com.safetychampion.data.visitor.domain.models.* // ktlint-disable no-wildcard-imports

internal class VisitorRemoteRepositoryImpl : BaseRepository(), IVisitorRemoteRepository {

    override suspend fun token(orgId: String, siteId: String, pin: String?): Result<VisitorToken> {
        val payload = VisitorPayload.Token(IdObject(orgId), IdObject(siteId), pin)
        return VisitorApi.Token(payload).call(null)
    }

    override suspend fun siteFetch(payload: VisitorPayload.SiteFetch): Result<VisitorSite> {
        return VisitorApi.SiteFetch(payload).call()
    }

    override suspend fun formFetch(payload: VisitorPayload.FormFetch): Result<VisitorForm> {
        return VisitorApi.FormFetch(payload).call()
    }

    override suspend fun evidencesFetch(payload: VisitorPayload.EvidencesFetch): Result<List<VisitorEvidence>> {
        return VisitorApi.EvidenceFetch(payload)
            .callAsList<VisitorEvidenceWrapper>()
            .flatMap { wrapper ->
                Result.Success(
                    wrapper.map { it.item }
                )
            }
    }

    override suspend fun arrive(payload: VisitorPayload.Arrive): Result<VisitorEvidence> {
        return VisitorApi.Arrive(
            body = payload
        ).call()
    }

    override suspend fun leave(payload: VisitorPayload.Leave): Result<VisitorEvidence> {
        return VisitorApi.Leave(body = payload).call()
    }

    override suspend fun evidenceFetch(body: VisitorPayload.EvidencesFetch): Result<List<VisitorEvidence>> {
        return VisitorApi.EvidenceFetch(body = body).callAsList()
    }
}
