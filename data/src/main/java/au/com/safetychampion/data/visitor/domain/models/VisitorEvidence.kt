package au.com.safetychampion.data.visitor.domain.models

import au.com.safetychampion.data.domain.models.Tier

/**
 * Created by Minh Khoi MAI on 21/12/20.
 */
data class VisitorEvidence(
    val _id: String,
    val type: String,
    var site: VisitorSite,
    val visitor: VisitorPayload.Visitor,
    val tier: Tier? = null,
    val arrive: VisitorFormEvidence,
    val leave: VisitorFormEvidence?,
    var token: String? = null,
    var isAutoSignOutActive: Boolean = false,
    var notificationId: Int? = null
) {
    /**
     * This method uses when update Evidence to DB but retaining some old data that is driven by local device
     */
    fun setRetainDataWhenUpdateDB(oldEvidence: VisitorEvidence) {
        this.token = oldEvidence.token
        this.isAutoSignOutActive = oldEvidence.isAutoSignOutActive
        this.notificationId = oldEvidence.notificationId
    }
}
