package au.com.safetychampion.data.visitor.domain.usecase.evidence

import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.core.SCError
import au.com.safetychampion.data.domain.core.flatMap
import au.com.safetychampion.data.visitor.domain.models.VisitorEvidence
import au.com.safetychampion.data.visitor.domain.models.VisitorPayload
import au.com.safetychampion.data.visitor.domain.models.VisitorSite
import au.com.safetychampion.util.koinInject

class FetchEvidenceUseCase {
    private val getLocalEvidence: GetLocalEvidenceUseCase by koinInject()
    private val fetchListEvidenceUseCase: FetchListEvidenceUseCase by koinInject()
    private val emptyError = { Result.Error(SCError.EmptyResult) } // TODO("Need a particular error")

    /**
     * Fetch a single [VisitorEvidence] fully from server, with [VisitorSite] already included from database.
     * @see GetLocalEvidenceUseCase
     * @see FetchListEvidenceUseCase
     */
    suspend operator fun invoke(evidenceId: String): Result<VisitorEvidence> {
        val localEvidence = getLocalEvidence.invoke(evidenceId) ?: return emptyError()
        val localSite = localEvidence.site
        // Fetch evidence with local non null token.
        val token = localEvidence.token ?: return emptyError()
        return fetchListEvidenceUseCase.invoke(
            VisitorPayload.EvidencesFetch(listOf(token))
        )
            .flatMap { newEvidences ->
                val evidence = newEvidences.find { it._id == evidenceId } ?: return@flatMap emptyError()
                // Include local visitor site
                evidence.site = localSite
                Result.Success(evidence)
            }
    }
}
