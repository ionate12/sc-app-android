package au.com.safetychampion.data.domain.models.incidents

import au.com.safetychampion.data.domain.Attachment
import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.core.Signature
import au.com.safetychampion.data.domain.models.ExternalBodyPojo
import au.com.safetychampion.data.domain.models.action.ActionLink
import au.com.safetychampion.data.domain.models.action.network.PendingActionPL
import au.com.safetychampion.data.domain.models.customvalues.CustomValue

data class IncidentTaskPL(
    override var attachments: MutableList<Attachment>?,
    override var categoryCusvals: MutableList<CustomValue>,
    val changeNotes: String,
    val changeImplemented: String,
    val complete: Boolean,
    val controlLevel: String,
    val controlReviewed: String,
    val controlReviewedOther: String,
    val controlLevelOther: String,
    val dateCompleted: String,
    override var cusvals: MutableList<CustomValue>,
    val externalBodiesNotified: List<ExternalBodyPojo>,
    val hasExternalBody: Boolean,
    val hazardCategory: String,
    val hazardCategoryOther: String,
    val lostTimeInjury: String,
    val links: ActionLink,
    override var pendingActions: MutableList<PendingActionPL>,
    override var propOrEnvDamageCusvals: MutableList<CustomValue>,
    val taskId: String,
    val title: String,
    val tzDateSignedoff: String,
    val severity: String,
    val sessionId: String,
    override var signatures: MutableList<Signature>?

) : BasePL(), IIncidentComponent {
    companion object {
        fun from(task: IncidentTask): IncidentTaskPL {
            TODO()
        }
    }
}
