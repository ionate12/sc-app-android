package au.com.safetychampion.data.domain.models.inspections

import android.graphics.Bitmap
import au.com.safetychampion.data.domain.* // ktlint-disable no-wildcard-imports
import au.com.safetychampion.data.domain.models.CreatedBy
import au.com.safetychampion.data.domain.models.GeoLatLng
import au.com.safetychampion.data.domain.models.Tier
import au.com.safetychampion.data.domain.models.TimeField
import au.com.safetychampion.data.domain.models.action.ActionPojo
import au.com.safetychampion.data.domain.uncategory.DocAttachment
import com.google.gson.annotations.SerializedName

data class InspectionFormPojo(
    var type: String? = null,
    var _id: String? = null,
    @SerializedName("for")
    var _for: Tier? = null,
    var tier: Tier? = null,
    var dateDue: String? = null,
    var complete: Boolean = false,
    var signoffNotes: String? = null,
    var executionMeta: ExecutionMeta? = null,
    var templateExecution: TemplateExecution? = null,
    var emailList: List<String>? = null,
    var attachments: MutableList<DocAttachment> = mutableListOf(),
    var signatures: List<Signature> = listOf(),
    var inExecution: Boolean? = null,
    var dateStarted: String? = null,
    var startedBy: CreatedBy? = null,
    var tzDateStarted: String? = null,
    var dateCommenced: String? = null,
    var snapshot: Snapshot? = null,
    var title: String? = null,
    var description: String? = null,
    var dateCompleted: String? = null,
    var dateDueFrom: String? = null,
    var tz: String? = null,
    var offlineRequest: InspectionOfflineSubTask? = null,
    var newActions: MutableList<ActionPojo> = mutableListOf()
) {
    data class Signature(
        val _id: String,
        val name: String,
        val file: Bitmap?

    )
    data class ExecutionMeta(
        var lead: String? = null,
        var team: String? = null,
        var location: String? = null,
        var notes: String? = null,
        var geoData: GeoLatLng? = null,
        var geoAddress: String? = null
    )
    data class Snapshot(
        var title: String? = null,
        var description: String? = null,
        var category: String? = null,
        var categoryOther: String? = null,
        var subcategory: String? = null,
        var subcategoryOther: String? = null,
        var frequencyKey: String? = null,
        var frequencyValue: Int? = null,
        var template: Template? = null,
        var templateMeta: InspectionTemplateMeta? = null,
        var emailList: List<String>? = null
    )
    data class TemplateExecution(
        var sections: List<TemplateExecutionSection>? = null,
        var cumulativeScorePercentage: Long? = null
    )
    data class TemplateExecutionSection(
        var _id: String? = null,
        var questions: List<ExecutionQuestion>? = null,
        var cumulativeScorePercentage: Long? = null
    )
    data class ExecutionQuestion(
        var _id: String? = null,
        var answer: Answer? = null
    )
    data class Answer(
        var links: List<Link>? = null,
        var date: String? = null,
        var time: TimeField? = null,
        var scoreIndex: Long? = null,
        var comment: String?
    )
    data class Link(
        var type: String? = null,
        var _id: String? = null,
        var reference: String? = null,
        var overview: String? = null,
        @Transient
        var questionId: String? = null
    )
    data class Template(
        var sections: List<TemplateSection>? = null
    )
    data class TemplateSection(
        var _id: String? = null,
        var title: String? = null,
        var description: String? = null,
        var questions: List<TemplateQuestion>? = null,
        var scoreOptions: List<ScoreOption>? = null,
        var cumulativeScorePercentage: Long? = null
    )
    data class TemplateQuestion(
        var _id: String? = null,
        var title: String? = null,
        var description: String? = null,
        var answerControl: AnswerControl? = null
    )
    data class AnswerControl(
        var comment: Long? = null,
        var date: Long? = null,
        var time: Long? = null,
        var attachments: Long? = null,
        var links: Long? = null,
        var nullScore: Boolean? = null
    )
    data class ScoreOption(
        var title: String? = null,
        var weight: Long? = null,
        var color: String? = null
    )
}
