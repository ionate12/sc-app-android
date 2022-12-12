package au.com.safetychampion.data.domain.models.reviewplan // ktlint-disable filename

import au.com.safetychampion.data.domain.models.customvalues.CustomValue

data class ReviewPlanClassification(
    val category: String,
    val subcategory: List<Subcategory>,
    val options: List<Option>,
    val cusvals: List<CustomValue>
) {

    data class Option(val opt: String, val value: Boolean)
    data class Subcategory(val title: String, val options: List<Option>, val cusvals: List<CustomValue>)
}
