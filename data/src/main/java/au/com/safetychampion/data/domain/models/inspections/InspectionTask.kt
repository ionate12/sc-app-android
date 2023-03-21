package au.com.safetychampion.data.domain.models.inspections

import au.com.safetychampion.data.data.common.OfflineRequest
import au.com.safetychampion.data.domain.Attachment
import au.com.safetychampion.data.domain.base.BaseModuleImpl
import au.com.safetychampion.data.domain.base.BaseTask
import au.com.safetychampion.data.domain.core.Signature
import au.com.safetychampion.data.domain.models.IAttachment
import au.com.safetychampion.data.domain.models.IExtraRequest
import au.com.safetychampion.data.domain.models.IPendingAction
import au.com.safetychampion.data.domain.models.ISignature
import au.com.safetychampion.data.domain.models.Tier
import au.com.safetychampion.data.domain.models.TimeField
import au.com.safetychampion.data.domain.models.action.ActionLink
import au.com.safetychampion.data.domain.models.action.network.PendingActionPL
import au.com.safetychampion.data.domain.models.location.GeoLatLng
import au.com.safetychampion.data.domain.models.workplace.CreatedBy
import com.google.gson.annotations.SerializedName

data class InspectionTask(
    override var type: String? = null,
    override var _id: String? = null,
    @SerializedName("for")
    override var _for: BaseModuleImpl? = null,
    override var tier: Tier? = null,
    override var dateDue: String? = null,
    override var complete: Boolean = false,
    var signoffNotes: String? = null,
    var executionMeta: ExecutionMeta? = null,
    var templateExecution: TemplateExecution? = null,
    var emailList: List<String> = listOf(),
    override var attachments: MutableList<Attachment> = mutableListOf(),
    override var inExecution: Boolean? = null,
    var dateStarted: String? = null,
    var startedBy: CreatedBy? = null,
    var tzDateStarted: String? = null,
    var dateCommenced: String? = null,
    var snapshot: Snapshot? = null,
    override var title: String? = null,
    override var description: String? = null,
    var dateCompleted: String? = null,
    var dateDueFrom: String? = null,
    var tz: String? = null,
    override var signatures: MutableList<Signature> = mutableListOf(),
    override var pendingActions: MutableList<PendingActionPL> = mutableListOf(),
    override var offlineRequest: OfflineRequest? = null
) : BaseTask, ISignature, IPendingAction, IAttachment, IExtraRequest {
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
        var links: List<ActionLink>? = null,
        var date: String? = null,
        var time: TimeField? = null,
        var scoreIndex: Int? = null,
        var comment: String?
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
