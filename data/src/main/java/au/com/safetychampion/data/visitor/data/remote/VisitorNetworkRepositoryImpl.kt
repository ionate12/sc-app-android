package au.com.safetychampion.data.visitor.data.remote // ktlint-disable filename

import au.com.safetychampion.data.data.BaseRepository
import au.com.safetychampion.data.data.api.VisitorApi
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.core.flatMap
import au.com.safetychampion.data.visitor.domain.models.* // ktlint-disable no-wildcard-imports

internal class VisitorRemoteRepositoryImpl : BaseRepository(), IVisitorRemoteRepository {

    override suspend fun token(orgId: String, siteId: String, pin: String?): Result<VisitorToken> {
        val payload = VisitorPayload.Token(IdObject(orgId), IdObject(siteId), pin)
        return VisitorApi.Token(payload).call("")
    }

    override suspend fun siteFetch(token: String): Result<VisitorSite> {
        val payload = VisitorPayload.SiteFetch(token)
        return VisitorApi.SiteFetch(payload).call()
    }

    override suspend fun formFetch(token: String, formId: String): Result<VisitorForm> {
        val payload = VisitorPayload.FormFetch(token, formId)
        return VisitorApi.FormFetch(payload).call()
    }

    override suspend fun visitFetch(tokens: List<String>): Result<List<VisitorEvidence>> {
        val payload = VisitorPayload.VisitFetch(tokens)
        return VisitorApi.VisitFetch(payload)
            .callAsList<VisitorEvidenceWrapper>()
            .flatMap { wrapper ->
                Result.Success(
                    wrapper.map { it.item }
                )
            }
    }

    override suspend fun arrive(token: String, profile: VisitorProfile, arriveForm: VisitorForm): Result<VisitorEvidence> {
        TODO()
    }

    override suspend fun leave(token: String, leaveForm: VisitorForm?): Result<VisitorEvidence> {
        TODO()
    }

    override suspend fun signOut(profile: VisitorProfile, site: VisitorSite): Result<Any> {
        TODO()
    }
}
