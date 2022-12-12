package au.com.safetychampion.data.domain.models.visitor
import au.com.safetychampion.data.domain.models.Tier
import au.com.safetychampion.data.domain.models.TimeField
import au.com.safetychampion.data.domain.models.customvalues.CustomValue

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
)

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
    val time: TimeField
)
