package au.com.safetychampion.data.visitor.domain.usecase

import au.com.safetychampion.data.domain.core.doOnSucceed
import au.com.safetychampion.data.domain.manager.IGsonManager
import au.com.safetychampion.data.visitor.domain.models.VisitorPayload
import au.com.safetychampion.data.visitor.domain.models.VisitorStatus
import au.com.safetychampion.util.koinInject

class LeaveAndUpdateUseCase : BaseVisitorUseCase() {
    private val gson: IGsonManager by koinInject()

    /**
     * Performs leave and then update database.
     */
    suspend operator fun invoke(
        evidenceId: String,
        payload: VisitorPayload.Leave
    ) {
        remoteRepository.leave(payload)
            .doOnSucceed { nEvidence ->
                val act = localRepository.getActivityEntity(evidenceId) ?: return@doOnSucceed
                act.apply {
                    data = gson.gson.toJsonTree(nEvidence).asJsonObject
                    isActive = false
                    status = VisitorStatus.OUT
                }
                localRepository.saveActivity(act)
            }
    }
}
