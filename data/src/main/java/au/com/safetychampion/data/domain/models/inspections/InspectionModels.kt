package au.com.safetychampion.data.domain

import au.com.safetychampion.data.domain.models.CreatedBy
import au.com.safetychampion.data.domain.models.Tier
import au.com.safetychampion.data.domain.models.TimeField
import au.com.safetychampion.data.domain.models.document.Document
import au.com.safetychampion.data.domain.models.inspections.InspectionFormPojo
import au.com.safetychampion.data.domain.models.trainning.task.Task
import au.com.safetychampion.data.domain.uncategory.* // ktlint-disable no-wildcard-imports

/**
 * Created by @Minh_Khoi_MAI on 16/7/20'
 * This will be used to parse JSON to models
 */

data class InspectionFetch(
    val type: String? = null,
    val _id: String? = null,
    val tier: Tier? = null,
    val allocatedToSelf: Boolean? = null,
    val title: String? = null,
    val childOf: Tier? = null,
    val description: String? = null,
    val frequencyKey: String? = null,
    val frequencyValue: Int? = null,
    val emailList: List<String>? = null,
    val category: String? = null,
    val categoryOther: String? = null,
    val subcategory: String? = null,
    val subcategoryOther: String? = null,
    val templateMeta: InspectionTemplateMeta? = null,
    val createdBy: CreatedBy? = null,
    val tzDateCreated: String? = null,
    val dateCreated: String? = null,
    val execute: Execute? = null,
    val template: InspectionFormPojo.Template? = null
) {
    data class Execute(
        val task: Task? = null
    )
}

data class InspectionFormPayload(
    var tz: String,
    var complete: Boolean = false,
    var dateCommenced: String,
    var signoffNotes: String,
    var emailList: List<String>,
    var executionMeta: InspectionFormPojo.ExecutionMeta,
    var templateExecution: TemplateExecutionPayload,
    var attachments: List<DocAttachment>,
    var signatures: List<SignaturePayload>,
    var dateDueFrom: String,
    var dateCompleted: String

) {
    data class TemplateExecutionPayload(
        val sections: List<SectionPayload>
    )

    data class SectionPayload(
        val _id: String,
        val questions: List<QuestionPayload>
    )

    data class QuestionPayload(
        val _id: String,
        val answer: AnswersPayload
    )
    data class AnswersPayload(
        var scoreIndex: Int?,
        var date: String?,
        var comment: String?,
        var time: TimeField?,
        var links: List<LinkPayload>
    )

    data class LinkPayload(
        val type: String,
        val _id: String,
        val reference: String
    )
}

data class SignaturePayload(val _id: String, val name: String)

/**
 * Created by @Minh_Khoi_MAI on 2020-02-17
 */
data class InspectionTemplateMeta(
    val locationPresets: List<String>,
    val labels: CustomLabel,
    val links: List<Document>,
    val control: Control
) {
    constructor() : this(listOf(), CustomLabel(), listOf(), Control())

    class CustomLabel(
        inspection: String,
        location: String,
        lead: String,
        team: String,
        notes: String
    ) {
        constructor() : this("", "", "", "", "")

        val module_name: String = TODO("ConfigurationUtils?")
        val inspection: String = inspection
            get() {
                return if (field.isNullOrEmpty()) {
                    module_name
                } else {
                    field
                }
            }
        val location: String = location
            get() {
                return if (field.isNullOrEmpty()) {
                    "Location where $module_name completed?"
                } else field
            }
        val lead: String = lead
            get() {
                return if (field.isNullOrEmpty()) {
                    "$module_name Leader*"
                } else field
            }
        val team: String = team
            get() {
                return if (field.isNullOrEmpty()) {
                    "$module_name Team"
                } else field
            }
        val notes: String = notes
            get() {
                return if (field.isNullOrEmpty()) {
                    "Other Details"
                } else field
            }
    }

    data class Control(
        val signatures: Int = 1, // 0 -> Not Allowed, 1 -> Optional, 2 -> Mandatory
        val geoloc: Int = 0
    )
}
