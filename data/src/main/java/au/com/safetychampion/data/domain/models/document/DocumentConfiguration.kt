package au.com.safetychampion.data.domain.models.document

import com.google.gson.JsonArray

data class DocumentConfiguration(
    val docModel: DocumentSignoff,
    var categoryMap: Map<String, List<String>>,
    var nextDeadLineList: MutableList<String>,
    private val vDocConfig: JsonArray?

) {
    // Generate List of Config
    init {
//        TODO("getCategoryMap")
//        categoryMap = getCategoryMap()
//        nextDeadLineList = mutableListOf(
//            "Due Date (${docModel.body.review?.task?.dateDue
//                ?: ""})",
//            "Date Completed"
//        )
    }
}
