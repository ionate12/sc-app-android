package au.com.safetychampion.data.visitor.domain.usecase.evidence

import au.com.safetychampion.data.visitor.data.VisitorActivityEntity
import au.com.safetychampion.data.visitor.data.VisitorEntityMapper
import au.com.safetychampion.data.visitor.domain.models.VisitorEvidence
import timber.log.Timber

internal class RetainEvidencesDataUseCase {
    private val mapper: VisitorEntityMapper by lazy { VisitorEntityMapper() }
    suspend operator fun invoke(
        fetchedEvidences: List<VisitorEvidence>,
        activities: List<VisitorActivityEntity>
    ): List<VisitorEvidence>? {
        if (fetchedEvidences.size != activities.size) {
            Timber.e("\"fetchedEvidences.size is not equals to activities.size, this function can not be used\"")
            return null
        }

        for (i in fetchedEvidences.indices) {
            if (fetchedEvidences[i]._id != activities[i]._id) {
                continue
            }
            fetchedEvidences[i].setRetainDataWhenUpdateDB(activities[i].toVisitorEvidence(mapper))
        }
        return fetchedEvidences
    }
}
