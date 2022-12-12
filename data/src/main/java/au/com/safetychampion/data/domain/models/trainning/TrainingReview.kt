package au.com.safetychampion.data.domain.models.trainning

import au.com.safetychampion.data.domain.models.CreatedBy
import au.com.safetychampion.data.domain.models.Tier
import au.com.safetychampion.data.domain.models.hr.HRProfile
import au.com.safetychampion.data.domain.uncategory.DocAttachment

data class TrainingReview(
    var type: String? = null,
    var _id: String? = null,
    var tier: Tier? = null,
    var owner: Tier? = null,
    var attachments: MutableList<DocAttachment>? = null,
    var name: String? = null,
    var nameOther: String? = null,
    var referenceId: String? = null,
    var dateIssued: String? = null,
    var dateExpiry: String? = null,
    var createdBy: CreatedBy? = null,
    var tzDateCreated: String? = null,
    var dateCreated: String? = null,

    // Extra Props
    var hrProfile: HRProfile? = null,
    var taskId: String? = null
)
