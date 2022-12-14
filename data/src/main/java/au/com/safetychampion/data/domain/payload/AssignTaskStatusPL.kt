package au.com.safetychampion.data.domain.payload

import au.com.safetychampion.data.domain.models.CreatedBy
import com.google.gson.annotations.SerializedName

data class AssignTaskStatusPL(
    val _id: String,
    @SerializedName("for")
    val _for: CreatedBy,
    var to: AssignUser? = null,
    var task: TaskInfo? = null
) : BasePL {

    data class TaskInfo(
        val title: String,
        val description: String,
        val module: String,
        val dateDue: String,
        val notes: String?
    )

    data class AssignUser(
        val _id: String,
        val type: String = "core.user"
    )
}
