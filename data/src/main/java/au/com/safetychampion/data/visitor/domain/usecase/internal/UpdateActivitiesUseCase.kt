package au.com.safetychampion.data.visitor.domain.usecase.internal

import au.com.safetychampion.data.domain.manager.IGsonManager
import au.com.safetychampion.data.visitor.data.VisitorActivityEntity
import au.com.safetychampion.data.visitor.domain.models.VisitorEvidence
import au.com.safetychampion.util.koinInject

internal class UpdateActivitiesUseCase : BaseVisitorUseCase() {
    private val retainEvidencesUseCase: RetainEvidencesUseCase by koinInject()
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
            )
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
        localRepository.insertActivity(*activities.toTypedArray())
    }
}
