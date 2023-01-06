package au.com.safetychampion.data.domain.models

import au.com.safetychampion.data.domain.models.task.Task

class TasksEnv(
    var items: List<Task>? = null
) {

    override fun toString(): String {
        return "TasksEnv{" +
            "items=" + items +
            '}'
    }
}
