package au.com.safetychampion.data.visitor.domain.usecase

import au.com.safetychampion.data.domain.manager.IGsonManager
import au.com.safetychampion.data.visitor.data.VisitorEntityMapper
import au.com.safetychampion.util.koinInject

class SetAutoSignoutUseCase : BaseVisitorUseCase() {
    private val mapper: VisitorEntityMapper = VisitorEntityMapper()
    private val gson: IGsonManager by koinInject()
    suspend operator fun invoke(evidenceId: String, isAutoSignOut: Boolean) {
        localRepository.getActivityEntity(evidenceId)
            ?.let { act ->
                act.toVisitorEvidence(mapper)?.apply {
                    isAutoSignOutActive = isAutoSignOut
                    generateNotificationId()
                    act.data = gson.gson.toJsonTree(this).asJsonObject
                    localRepository.saveActivity(act)
                }
            }
    }
}
