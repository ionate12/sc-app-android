package au.com.safetychampion.data.domain.models.incidents

import au.com.safetychampion.data.domain.Attachment
import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.core.Signature
import au.com.safetychampion.data.domain.models.IPendingActionPL
import au.com.safetychampion.data.domain.models.action.ActionLink
import au.com.safetychampion.data.domain.models.action.network.PendingActionPL
import au.com.safetychampion.data.domain.models.customvalues.CustomValue

data class IncidentTaskPL(
    override var attachments: MutableList<Attachment>,
    override var categoryCusvals: MutableList<CustomValue>,
    val changeNotes: String?,
    val changeImplemented: String?,
    val complete: Boolean,
    val controlLevel: String?,
    val controlReviewed: String?,
    val controlReviewedOther: String?,
    val controlLevelOther: String?,
    val dateCompleted: String?,
    override var cusvals: MutableList<CustomValue>,
    val externalBodiesNotified: List<ExternalBodyPojo>,
    val externalBodiesNotifiedYesNo: Boolean? = null,
    val hazardCategory: String?,
    val hazardCategoryOther: String?,
    val lostTimeInjury: String?,
    val links: List<ActionLink>,
    override var pendingActions: MutableList<PendingActionPL>,
    override var propOrEnvDamageCusvals: MutableList<CustomValue>,
    val _id: String,
    val title: String?,
    val tzDateSignedoff: String?,
    val severity: String?,
    val sessionId: String?,
    override var signatures: MutableList<Signature>
) : BasePL(), IIncidentComponent, IPendingActionPL {
    companion object {
        fun fromModel(model: IncidentTask): IncidentTaskPL {
            return IncidentTaskPL(
                attachments = mutableListOf(),
                categoryCusvals = mutableListOf(),
                changeNotes = model.changeNote,
                changeImplemented = model.changeImplemented,
                complete = model.complete ?: false,
                controlLevel = model.controlLevel,
                controlReviewed = model.controlReviewed,
                controlReviewedOther = model.controlReviewedOther,
                controlLevelOther = model.controlLevelOther,
                dateCompleted = model.dateCompleted,
                cusvals = model.cusvals,
                externalBodiesNotified = model.externalBodiesNotified,
                externalBodiesNotifiedYesNo = model.hasExternalBody,
                hazardCategory = model.hazardCategory,
                hazardCategoryOther = model.hazardCategoryOther,
                lostTimeInjury = model.lostTimeInjury,
                links = model.links,
                pendingActions = mutableListOf(),
                propOrEnvDamageCusvals = mutableListOf(),
                _id = model._id ?: "",
                title = model.title,
                tzDateSignedoff = model.tzDateSignedoff,
                severity = model.severity,
                sessionId = model.sessionId,
                signatures = model.signatures
            )
        }
    }

    override fun onPendingActionsCreated(links: List<ActionLink>): BasePL {
        return this.copy(links = this.links + links)
    }
}
