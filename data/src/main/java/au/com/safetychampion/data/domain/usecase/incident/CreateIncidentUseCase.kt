package au.com.safetychampion.data.domain.usecase.incident

import au.com.safetychampion.data.data.incident.IIncidentRepository
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.incidents.Incident
import au.com.safetychampion.data.domain.models.incidents.IncidentNewPL

class CreateIncidentUseCase(
    private val repository: IIncidentRepository
) {
    suspend operator fun invoke(body: IncidentNewPL): Result<Incident> {
        return repository.new(body)
    }

    /** could be moved to view model if needed */
    suspend operator fun invoke(
        body: IncidentNewPL,
        expandInjurySection: Boolean,
        hasInjuredBodyParts: Boolean
    ): Result<Incident> {
        if (!expandInjurySection) {
            body.apply {
                injuredPersonName = null
                injuredPersonPhone = null
                injuredPersonRole = null
                injuredPersonRoleOther = null
                injuryDescription = null
            }
        }
        if (hasInjuredBodyParts) { body.injuredBodyParts = null }
        return invoke(body)
    }
}
