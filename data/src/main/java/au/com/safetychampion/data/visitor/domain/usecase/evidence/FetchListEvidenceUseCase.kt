package au.com.safetychampion.data.visitor.domain.usecase.evidence

import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.core.doOnSuccess
import au.com.safetychampion.data.domain.manager.IGsonManager
import au.com.safetychampion.data.util.extension.koinInject
import au.com.safetychampion.data.visitor.data.VisitorActivityEntity
import au.com.safetychampion.data.visitor.domain.models.VisitorEvidence
import au.com.safetychampion.data.visitor.domain.models.VisitorPayload
import au.com.safetychampion.data.visitor.domain.usecase.BaseVisitorUseCase

class FetchListEvidenceUseCase : BaseVisitorUseCase() {
    private val updateActivitiesUseCase: UpdateActivitiesUseCase by koinInject()

    /**
     * Fetch an evidence list remotely.
     */
    suspend operator fun invoke(payload: VisitorPayload.EvidencesFetch): Result<List<VisitorEvidence>> {
        return remoteRepository.evidencesFetch(payload)
            .doOnSuccess { evidences ->
                updateActivitiesUseCase.invoke(
                    fetched = evidences,
                    activities = localRepository.getActivitiesEntity(
                        idList = evidences.map { it._id }
                    ) ?: emptyList()
                )
            }
    }
}

internal class UpdateActivitiesUseCase : BaseVisitorUseCase() {
    private val retainEvidencesUseCase: RetainEvidencesDataUseCase by koinInject()
    private val gson: IGsonManager by koinInject()

    suspend operator fun invoke(
        fetched: List<VisitorEvidence>,
        activities: List<VisitorActivityEntity>
    ) {
        if (fetched.size == activities.size) {
            // 1a. Retain and Update
            val retainedEvidences = retainEvidencesUseCase.invoke(
                fetchedEvidences = fetched,
                activities = activities
            )!! // not null.

            activities.forEachIndexed { i, activity ->
                activity.data = gson.gson.toJsonTree(retainedEvidences[i]).asJsonObject
                activity.isActive = retainedEvidences[i].leave != null
            }
        } else {
            // 1b. Update data with out retain
            activities.forEach { activity ->
                fetched.find { it._id == activity._id }
                    ?.let { activity.data = gson.gson.toJsonTree(it).asJsonObject }
            }
        }
        // 2. Insert
        localRepository.saveActivities(activities)
    }
}
