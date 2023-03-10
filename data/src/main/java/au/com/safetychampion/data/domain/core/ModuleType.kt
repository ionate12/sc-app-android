package au.com.safetychampion.data.domain.core

enum class ModuleType(val code: String, val value: String) {
    USER("core.user", "User"),
    ACTION("core.module.action", "Action"),
    ADMIN("core.module.admin2", "Admin"),
    CHEMICAL("core.module.chemical", "Chemical"),
    CONTRACTOR("core.module.contractor", "Contractor"),
    CRISK("core.module.crisk", "Crisk"),
    HR("core.module.hr", "Human Resources"),
    INCIDENT("core.module.incident", "Incident"),
    INSPECTION("core.module.inspection", "Inspection"),
    NOTICEBOARD("core.module.noticeboard", "Notice Board"),
    REVIEW_PLAN("core.module.reviewplan", "Insurance"),
    SAFETY_PLAN("core.module.safetyplan", "Safety Plan"),
    THEME("core.module.theme", "Theme"),
    TRAINING("core.module.training", "Training"),
    DOCUMENT("core.module.vdoc", "Document"),
    NOT_SUPPORTED("core.module.unknown", "Unknown");

    companion object {
        fun fromString(value: String): ModuleType =
            values().find { it.code == value } ?: NOT_SUPPORTED
    }
}
