package au.com.safetychampion.data.domain.models.chemical

import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.uncategory.Constants
import au.com.safetychampion.data.domain.uncategory.DocAttachment

data class ChemicalTask(
    val attachments: MutableList<DocAttachment> = mutableListOf(),
    var complete: Boolean = false,
    var completionNotes: String? = null,
    var dateCompleted: String? = null,
    var dateSdsIssued: String? = null,
    var dateToReview: String? = null,
    var hasSds: Boolean = true,
    var name: String? = null,
    var purpose: String? = null,
    var recurrent: Boolean = true,
    var tzDateSignedoff: String? = Constants.tz
) : BasePL()
