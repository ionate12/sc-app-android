package au.com.safetychampion.data.domain.models.inspections.payload

import au.com.safetychampion.data.domain.Attachment
import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.core.Signature
import au.com.safetychampion.data.domain.models.IAttachment
import au.com.safetychampion.data.domain.models.IPendingActionPL
import au.com.safetychampion.data.domain.models.ISignature
import au.com.safetychampion.data.domain.models.TimeField
import au.com.safetychampion.data.domain.models.action.ActionLink
import au.com.safetychampion.data.domain.models.action.network.PendingActionPL
import au.com.safetychampion.data.domain.models.inspections.InspectionTask
import au.com.safetychampion.data.domain.uncategory.Constants

data class InspectionTaskPL(
    var tz: String = Constants.tz,
    var complete: Boolean = false,
    var dateCommenced: String,
    var signoffNotes: String,
    var emailList: List<String>,
    var executionMeta: InspectionTask.ExecutionMeta,
    var templateExecution: TemplateExecutionPayload,
    var dateDueFrom: String,
    var dateCompleted: String,
    override var signatures: MutableList<Signature>,
    override var attachments: MutableList<Attachment>,
    override var pendingActions: MutableList<PendingActionPL>
) : BasePL(), IAttachment, ISignature, IPendingActionPL {

    override fun onPendingActionsCreated(links: List<ActionLink>): InspectionTaskPL {
        // Map to questions.
        templateExecution.sections.flatMap { it.questions }.forEach { question ->
            links.find { question._id == it.refId }?.let {
                question.answer.links = question.answer.links + it
            }
        }
        return this
    }

    companion object {
        fun fromModel(model: InspectionTask) =
            InspectionTaskPL(
                complete = model.complete,
                signoffNotes = model.signoffNotes!!,
                emailList = model.emailList,
                dateCommenced = model.dateCommenced!!,
                executionMeta = model.executionMeta!!,
                templateExecution = TemplateExecutionPayload.fromModel(model.templateExecution),
                dateDueFrom = model.dateDueFrom!!,
                dateCompleted = model.dateCompleted!!,
                signatures = model.signatures,
                attachments = model.attachments,
                pendingActions = model.pendingActions
            )
    }
    data class TemplateExecutionPayload(
        val sections: List<SectionPayload>
    ) {
        companion object {
            internal fun fromModel(model: InspectionTask.TemplateExecution?) =
                TemplateExecutionPayload(model?.sections?.map { SectionPayload.fromModel(it) } ?: listOf())
        }
    }

    data class SectionPayload(
        val _id: String,
        val questions: List<QuestionPayload>
    ) {
        companion object {
            internal fun fromModel(model: InspectionTask.TemplateExecutionSection) =
                SectionPayload(
                    _id = model._id!!,
                    questions = model.questions?.map { QuestionPayload.fromModel(it) } ?: listOf()
                )
        }
    }

    data class QuestionPayload(
        val _id: String,
        val answer: AnswersPayload
    ) {
        companion object {
            internal fun fromModel(model: InspectionTask.ExecutionQuestion) = QuestionPayload(
                _id = model._id!!,
                answer = with(model.answer!!) {
                    AnswersPayload(
                        scoreIndex = this.scoreIndex,
                        date = date,
                        time = time,
                        links = links ?: listOf(),
                        comment = comment
                    )
                }
            )
        }
    }
    data class AnswersPayload(
        var scoreIndex: Int?,
        var date: String?,
        var comment: String?,
        var time: TimeField?,
        var links: List<ActionLink>
    )
}
