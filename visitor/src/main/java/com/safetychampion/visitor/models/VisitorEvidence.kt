package au.com.safetychampion.scmobile.visitorModule.models

import au.com.safetychampion.scmobile.database.tier.Tier
import au.com.safetychampion.scmobile.modules.incident.TimeField
import au.com.safetychampion.scmobile.modules.incident.model.CustomValue
import au.com.safetychampion.scmobile.visitorModule.VisitorUtil
import java.util.*

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
        var notificationId: Int? = null,
) {
    //Unit method to generate a unique Notif Id for each evidence
    fun generateNotificationId(): Int {
        if(notificationId == null)
            notificationId = (Date().time % 1000000).toInt() + 3000000
        return notificationId!!
    }


    fun getOrgAndTitle() = "${site.tier.name} - ${site.title}"

    /**
     * This method uses when update Evidence to DB but retaining some old data that is driven by local device
     */
    fun setRetainDataWhenUpdateDB(oldEvidence: VisitorEvidence) {
        this.token = oldEvidence.token
        this.isAutoSignOutActive = oldEvidence.isAutoSignOutActive
        this.notificationId = oldEvidence.notificationId
    }
}

data class VisitorEvidenceWrapper(
        val tokenIndex: Int,
        val item: VisitorEvidence
)

data class VisitorFormEvidence(
        val type: String,
        val _id: String,
        val cusvals: List<CustomValue>,
        val tz: String,
        val date: String,
        val time: TimeField) {

    fun displayDateTime(): String = VisitorUtil.dateTime(date, time.toString())
}