package au.com.safetychampion.data.domain.usecase.assigntaskstatus

import au.com.safetychampion.data.data.common.TaskRepository
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.extensions.jsonObjectOf
import au.com.safetychampion.data.domain.models.CreatedBy
import au.com.safetychampion.data.domain.models.TaskAssignStatusItem
import au.com.safetychampion.data.domain.models.task.Task
import au.com.safetychampion.data.domain.models.task.TaskPayload

class AssignTaskStatusItemUseCase(
    private val activeTaskRepository: TaskRepository
) {
    suspend operator fun invoke(
        task: Task,
        userId: String? = null,
        notes: String? = null,
        moduleName: String?,
        dateDue: String?
    ): Result<List<TaskAssignStatusItem>> {
        val body = jsonObjectOf(
            TaskPayload.AssignTask(
                _id = task._id,
                _for = CreatedBy(
                    type = task._for.type,
                    _id = task._for._id
                ),
                to = userId?.let { TaskPayload.AssignUser(it) },
                task = if (userId == null) null else TaskPayload.TaskInfo(
                    dateDue = dateDue ?: "",
                    notes = notes,
                    title = task.title,
                    description = task.description,
                    module = moduleName ?: ""
                )
            )
        )
        return activeTaskRepository.assignTaskStatus(body!!)
    }
}
