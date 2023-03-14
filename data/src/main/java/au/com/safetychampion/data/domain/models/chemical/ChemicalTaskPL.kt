package au.com.safetychampion.data.domain.models.chemical

import au.com.safetychampion.data.domain.Attachment
import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.models.IAttachment

data class ChemicalTaskPL(
    val complete: Boolean = true,
    val tzDateSignedoff: String,
    val completionNotes: String,
    val dateCompleted: String,
    val recurrent: Boolean,
    val name: String,
    val purpose: String,
    val dateSdsIssued: String?,
    val dateToReview: String?,
    override var attachments: MutableList<Attachment>
) : BasePL(), IAttachment {
    companion object {
        fun fromModel(model: ChemicalTask): ChemicalTaskPL {
            return ChemicalTaskPL(
                complete = model.complete ?: false,
                tzDateSignedoff = model.tzDateSignedoff ?: "",
                completionNotes = model.completionNotes ?: "",
                dateCompleted = model.dateCompleted ?: "",
                recurrent = model.recurrent,
                name = model.name ?: "",
                purpose = model.purpose ?: "",
                dateSdsIssued = model.dateSdsIssued,
                dateToReview = model.dateToReview ?: "",
                attachments = model.attachments
            )
        }
    }
}
