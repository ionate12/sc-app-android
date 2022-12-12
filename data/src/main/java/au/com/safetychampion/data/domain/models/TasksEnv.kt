package au.com.safetychampion.data.domain.models

class TasksEnv(
    var items: List<Task>? = null
) {

    override fun toString(): String {
        return "TasksEnv{" +
            "items=" + items +
            '}'
    }
}
