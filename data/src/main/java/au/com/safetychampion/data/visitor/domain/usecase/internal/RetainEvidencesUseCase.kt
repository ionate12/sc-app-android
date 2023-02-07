package au.com.safetychampion.data.visitor.domain.usecase.internal

import au.com.safetychampion.data.visitor.data.VisitorActivityEntity
import au.com.safetychampion.data.visitor.data.VisitorEntityMapper
import au.com.safetychampion.data.visitor.domain.models.VisitorEvidence

internal class RetainEvidencesUseCase() {
    private val mapper: VisitorEntityMapper by lazy { VisitorEntityMapper() }

    suspend operator fun invoke(
        fetchedEvidences: List<VisitorEvidence>,
        activities: List<VisitorActivityEntity>
    ): List<VisitorEvidence> {
        val evidences = fetchedEvidences.toMutableList()
        for (i in evidences.indices) {
            if (evidences[i]._id != activities[i]._id) {
                continue
            }
            evidences[i].setRetainDataWhenUpdateDB(activities[i].toVisitorEvidence(mapper))
        }
        return evidences
    }
}
