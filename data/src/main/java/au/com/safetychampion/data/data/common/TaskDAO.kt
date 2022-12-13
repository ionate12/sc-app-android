package au.com.safetychampion.data.data.common

import au.com.safetychampion.data.domain.models.Task

interface TaskDAO {
    fun insert(vararg tasks: Task?)
}
