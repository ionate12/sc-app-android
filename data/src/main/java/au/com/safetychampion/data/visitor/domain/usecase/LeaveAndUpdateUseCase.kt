package au.com.safetychampion.data.visitor.domain.usecase

import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.core.doOnSuccess
import au.com.safetychampion.data.domain.manager.IGsonManager
import au.com.safetychampion.data.util.extension.koinInject
import au.com.safetychampion.data.visitor.domain.models.VisitorEvidence
import au.com.safetychampion.data.visitor.domain.models.VisitorPayload
import au.com.safetychampion.data.visitor.domain.models.VisitorStatus

class LeaveAndUpdateUseCase : BaseVisitorUseCase() {
    private val gson: IGsonManager by koinInject()

    /**
     * Performs leave and then update database.
     */
    suspend operator fun invoke(
        evidenceId: String,
        payload: VisitorPayload.Leave
    ): Result<VisitorEvidence> {
        return remoteRepository.leave(payload)
            .doOnSuccess { nEvidence ->
                val act = localRepository.getActivityEntity(evidenceId) ?: return@doOnSuccess
                act.apply {
                    data = gson.gson.toJsonTree(nEvidence).asJsonObject
                    isActive = false
                    status = VisitorStatus.OUT
                }
                localRepository.saveActivity(act)
            }
    }
}
