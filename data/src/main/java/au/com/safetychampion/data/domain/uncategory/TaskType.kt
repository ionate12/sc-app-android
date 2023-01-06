package au.com.safetychampion.data.domain.uncategory

object TaskType {
    /**
     * TASK TYPE FOLLOWS MODULE TYPE SYSTEM IN BACKEND. MUST NOT CHANGE.!!!!
     */
    // MORPH
    const val MORPH_DOWN = "core.module.morph.down"
    const val UNMORPH = "core.module.morph.up"
    const val TASK = "core.module.task"

    // ACTION
    const val ACTION = "core.module.action"
    const val ACTION_VIEW = "core.module.action.view"
    const val ACTION_FETCH = "core.module.action.fetch"
    const val ACTION_FETCH_FROM_ACT = "core.module.action.fetchonly"
    const val ACTION_NEW = "core.module.action.submit"
    const val ACTION_EDIT = "core.module.action.edit"
    const val ACTION_SIGNOFF = "core.module.action.signoff"
    const val SIGN_OFF = "core.module.general.signoff"

    // INSPECTION
    const val INSPECTION = "core.module.inspection"
    const val INSPECTION_FETCH = "core.module.inspection.fetch"
    const val INSPECTION_START = "core.module.inspection.start"
    const val INSPECTION_SIGNOFF = "core.module.inspection.signoff"
    const val INSPECTION_SIGNOFF_SAVE = "core.module.inspection.signoff.save"
    const val INSPECTION_NEWCHILD = "core.module.inspection.newChild"
    const val INSPECTION_LINKEDDOCS = "core.module.inspection.links.vdocs"

    // INCIDENT
    const val INCIDENT = "core.module.incident"
    const val INCIDENT_VIEW = "core.module.incident.view"
    const val INCIDENT_FETCH = "core.module.incident.fetch"
    const val INCIDENT_FETCH_ONLY = "core.module.incident.fetchonly"
    const val INCIDENT_NEW = "core.module.incident.submit"
    const val INCIDENT_SIGNOFF = "core.module.incident.signoff"
    const val INCIDENT_CONF_LOCATIONS = "core.module.incident.configuredlocations"
    const val INCIDENT_LOOKUP = "core.module.incident.hrlookup"

    // DOCUMENT
    const val DOCUMENT = "core.module.vdoc"
    const val DOCUMENT_FETCH = "core.module.vdoc.fetch"
    const val DOCUMENT_VERSION = "core.module.vdoc.version"
    const val DOCUMENT_SIGNOFF = "core.module.vdoc.signoff"

    // CHEMICAL
    const val CHEMICAL = "core.module.chemical"
    const val CHEMICAL_SIGNOFF = "core.module.chemical.signoff"
    const val CHEMICAL_FETCH = "core.module.chemical.fetch"
    const val CHEMICAL_GHS_CODE = "core.module.chemical.ghscode"

    // SAFETY PLAN
    const val SAFETY_PLAN = "core.module.safetyplan"
    const val SAFETY_PLAN_SIGNOFF = "core.module.safetyplan.signoff"

    // HR
    const val HR = "core.module.hr"
    const val HR_FETCH = "core.module.hr.fetch"
    const val HR_LINKED_INCIDENT = "core.module.hr.linkedincident"

    // TRAINING
    const val TRAINING = "core.module.training"
    const val TRAINING_FETCH = "core.module.training.fetch"
    const val TRAINING_SIGNOFF = "core.module.training.signoff"

    // REVIEW PLAN
    const val REVIEW_PLAN = "core.module.reviewplan"
    const val REVIEW_PLAN_FETCH = "core.module.reviewplan.fetch"
    const val REVIEW_PLAN_VIEW = "core.module.reviewplan.view"
    const val REVIEW_PLAN_SIGNOFF = "core.module.reviewplan.signoff"

    // CONTRACTOR
    const val CONTRACTOR = "core.module.contractor"
    const val CONTRACTOR_VIEW = "core.module.contractor.view"
    const val CONTRACTOR_FETCH = "core.module.contractor.fetch"
    const val CONTRACTOR_LINKED_TASKS = "core.module.contractor.linkedtasks"

    // VISITOR
    const val VISITOR = "core.module.visitor"

    // NOTICEBOARD
    const val NOTICE_BOARD = "core.module.noticeboard"
    const val NOTICE_BOARD_FORM = "core.module.noticeboard.form"
    const val NOTICE_BOARD_BOARD = "core.module.noticeboard.board"

    // THEME
    const val THEME = "core.module.theme"

    // CRISK
    const val CRISK_SIGNOFF = "core.module.crisk.signoff"
    const val CRISK = "core.module.crisk"
    const val CRISK_LIST = "core.module.crisk.list"
    const val CRISK_FETCH = "core.module.crisk.fetch"
    const val CRISK_FETCHONLY = "core.module.crisk.fetchonly"
    fun getFetchType(taskType: String): String {
        return "$taskType.fetch"
    }
}
