package au.com.safetychampion.data.domain.models

import au.com.safetychampion.data.domain.models.crisk.CriskEvidenceTask
import au.com.safetychampion.data.domain.models.reviewplan.ReviewPlanEvidenceTask

class UpdateLogListItem(by: UpdateBy, comment: String, date: String) : UpdateLog(by, comment, date) {
    companion object {
        fun fromReviewPlanEvidenceTask(task: ReviewPlanEvidenceTask): UpdateLogListItem {
            val _by = UpdateBy(
                task.signedoffBy?.email ?: "",
                task.signedoffBy?.name ?: "",
                task.signedoffBy?.type ?: "",
                task.signedoffBy?._id ?: ""
            )
            return UpdateLogListItem(_by, task.dateSignedoff ?: "", "")
        }
        fun fromCriskEvidence(task: CriskEvidenceTask): UpdateLogListItem {
            val _by = UpdateBy(
                task.signedoffBy?.email ?: "",
                task.signedoffBy?.name ?: "",
                task.signedoffBy?.type ?: "",
                task.signedoffBy?._id ?: ""
            )
            return UpdateLogListItem(_by, task.dateSignedoff ?: "", "")
        }
    }
    override fun printTitle(): String {
        return "${by.name} - ${by.email}"
    }
}
