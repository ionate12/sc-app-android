package au.com.safetychampion.data.domain.models.action.network

import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.models.CreatedBy
import au.com.safetychampion.data.domain.models.trainning.task.Task
import com.google.gson.annotations.SerializedName

class AssignTaskStatusPL(
    val _id: String,
    @SerializedName("for")
    val _for: CreatedBy,
    var to: AssignUser? = null,
    var task: TaskInfo? = null
) : BasePL() {
    constructor(
        task: Task,
        userId: String? = null,
        notes: String? = null,
        moduleName: String? = null,
        dateDue: String? = null
    ) : this(
        _id = task._id,
        _for = CreatedBy(
            type = task._for.type ?: "",
            _id = task._for._id
        ),
        to = userId?.let { AssignUser(it) },
        task = if (userId == null) null else TaskInfo(
            dateDue = dateDue ?: "",
            notes = notes,
            title = task.title,
            description = task.description,
            module = moduleName ?: ""
        )
    )
}

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
