package au.com.safetychampion.data.domain.core

enum class ModuleName(val code: String, val value: String) {
    ACTION("core.module.action", "Action"),
    CHEMICAL("core.module.chemical", "Chemical"),
    CONTRACTOR("core.module.contractor", "Contractor"),
    CRISK("core.module.crisk", "Crisk"),
    DOCUMENT("core.module.vdoc", "Document"),
    HR("core.module.hr", "Human Resources"),
    INSPECTION("core.module.inspection", "Inspection"),
    INCIDENT("core.module.incident", "Incident"),
    NOTICE_BOARD("core.module.noticeboard", "Noticeboard"),
    REVIEW_PLAN("core.module.reviewplan", "Review Plan"),
    SAFETY_PLAN("core.module.safetyplan", "Safety Plan"),
    TRAINING("core.module.training", "Training"),
    VISITOR("core.module.visitor", "Visitor")
}
