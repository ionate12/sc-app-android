package au.com.safetychampion.data.domain.models.inspections

import au.com.safetychampion.data.domain.core.ModuleType
import au.com.safetychampion.data.domain.models.Tier
import au.com.safetychampion.data.domain.models.task.Task
import au.com.safetychampion.data.domain.models.workplace.CreatedBy

data class Inspection(
    val type: ModuleType = ModuleType.INSPECTION,
    val _id: String,
    val tier: Tier,
    val allocatedToSelf: Boolean? = null,
    val title: String,
    val childOf: ChildOf? = null,
    val description: String? = null,
    val frequencyKey: String? = null,
    val frequencyValue: Int? = null,
    val emailList: List<String> = listOf(),
    val category: String,
    val categoryOther: String? = null,
    val subcategory: String,
    val subcategoryOther: String? = null,
    val templateMeta: InspectionTemplateMeta? = null,
    val createdBy: CreatedBy? = null,
    val tzDateCreated: String? = null,
    val dateCreated: String? = null,
    val execute: Execute? = null,
    val template: InspectionTask.Template? = null
) {
    data class Execute(
        val task: Task? = null
    )

    data class ChildOf(
        val _id: String,
        val type: ModuleType = ModuleType.INSPECTION
    )
}
