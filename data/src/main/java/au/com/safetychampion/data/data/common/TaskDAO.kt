package au.com.safetychampion.data.data.common

import au.com.safetychampion.data.domain.models.task.Task

interface TaskDAO {
    fun insert(vararg tasks: Task?)
}
