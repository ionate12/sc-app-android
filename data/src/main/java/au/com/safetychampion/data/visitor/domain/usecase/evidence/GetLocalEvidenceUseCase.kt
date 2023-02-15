package au.com.safetychampion.data.visitor.domain.usecase.evidence

import au.com.safetychampion.data.visitor.data.VisitorEntityMapper
import au.com.safetychampion.data.visitor.domain.models.VisitorEvidence
import au.com.safetychampion.data.visitor.domain.models.VisitorSite
import au.com.safetychampion.data.visitor.domain.usecase.BaseVisitorUseCase

internal class GetLocalEvidenceUseCase : BaseVisitorUseCase() {
    private val mapper = VisitorEntityMapper()

    /**
     * @return a [VisitorEvidence] already included [VisitorSite], both are retrieved from database.
     */
    suspend operator fun invoke(evidenceId: String): VisitorEvidence? {
        val actEntity = localRepository.getActivityEntity(evidenceId) ?: return null
        val evidence = actEntity.toVisitorEvidence(mapper) ?: return null
        val site = localRepository.getSite(actEntity._id) ?: return null
        if (evidence.site._id == site._id) {
            evidence.site = site
        }
        return evidence
    }
}
