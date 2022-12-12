package au.com.safetychampion.data.domain.models

data class TaskAssignStatusItem(
    val type: String,
    val _id: String,
    val name: String,
    var tier: Tier,
    var optionalMessage: String? = null,
    var assigned: Boolean = false,
    var requestStatusChange: StatusChange? = null,
    var isCommentActivated: Boolean = false
) {
    enum class StatusChange {
        SUCCESS, FAILURE, NOTHING
    }
}
