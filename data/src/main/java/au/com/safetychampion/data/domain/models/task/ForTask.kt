package au.com.safetychampion.data.domain.models.task

data class ForTask(
    var type: String?,
    var name: String? = null,
    var _id: String? = null,
    var actionType: Int
) {

    companion object {
        const val CREATE_ACTION = 1
        const val EDIT_ACTION = 2
    }
}
