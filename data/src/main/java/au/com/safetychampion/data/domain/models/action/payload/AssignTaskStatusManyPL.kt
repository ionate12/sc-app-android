package au.com.safetychampion.data.domain.models.action.payload

import au.com.safetychampion.data.domain.base.BaseModuleImpl
import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.models.task.Task
import com.google.gson.annotations.SerializedName

data class AssignTaskStatusManyPL(
    val ids: List<Info>
) : BasePL() {
    data class Info(
        @SerializedName("for")
        val _for: BaseModuleImpl,
        val _id: String
    )

    companion object {
        fun fromTasks(tasks: List<Task>): AssignTaskStatusManyPL {
            return AssignTaskStatusManyPL(tasks.map { Info(it._for, it._id) })
        }
    }
}
