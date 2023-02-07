package au.com.safetychampion.data.visitor.domain.usecase

import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.core.doOnSucceed
import au.com.safetychampion.data.visitor.domain.models.VisitorEvidence
import au.com.safetychampion.data.visitor.domain.usecase.internal.BaseVisitorUseCase
import au.com.safetychampion.data.visitor.domain.usecase.internal.UpdateActivitiesUseCase
import au.com.safetychampion.util.koinInject

class FetchVisitorEvidenceUseCase : BaseVisitorUseCase() {
    private val updateActivitiesUseCase: UpdateActivitiesUseCase by koinInject()

    suspend operator fun invoke(vararg fetchedEvidences: VisitorEvidence): Result<List<VisitorEvidence>> {
        return remoteRepository.visitFetch(
            // Pass a non null token list
            fetchedEvidences.mapNotNull {
                if (it.token == null) null else it.token
            }
        )
            .doOnSucceed { evidences ->
                updateActivitiesUseCase.invoke(
                    fetched = evidences,
                    activities = localRepository.getActivities(
                        idList = evidences.map { it._id }
                    ) ?: emptyList()
                )
            }
    }
}
