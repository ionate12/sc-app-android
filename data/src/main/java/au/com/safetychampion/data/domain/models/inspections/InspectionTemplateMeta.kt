package au.com.safetychampion.data.domain.models.inspections

import au.com.safetychampion.data.domain.models.document.Document

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
        inspection: String = "",
        location: String = "",
        lead: String = "",
        team: String = "",
        notes: String = ""
    ) {
//        constructor() : this("", "", "", "", "")
//
//
//        val inspection: String = inspection
//            get() {
//                return if (field.isNullOrEmpty()) {
//                    module_name
//                } else {
//                    field
//                }
//            }
//        val location: String = location
//            get() {
//                return if (field.isNullOrEmpty()) {
//                    "Location where $module_name completed?"
//                } else field
//            }
//        val lead: String = lead
//            get() {
//                return if (field.isNullOrEmpty()) {
//                    "$module_name Leader*"
//                } else field
//            }
//        val team: String = team
//            get() {
//                return if (field.isNullOrEmpty()) {
//                    "$module_name Team"
//                } else field
//            }
//        val notes: String = notes
//            get() {
//                return if (field.isNullOrEmpty()) {
//                    "Other Details"
//                } else field
//            }
    }

    data class Control(
        val signatures: Int = 1, // 0 -> Not Allowed, 1 -> Optional, 2 -> Mandatory
        val geoloc: Int = 0
    )
}
