package au.com.safetychampion.data.domain.manager

import au.com.safetychampion.data.domain.models.OfflineTask
import au.com.safetychampion.data.domain.usecase.action.SignoffParams

interface IOfflineConverter {
    fun toOfflineTask(taskData: SignoffParams): OfflineTask
    fun <T> toObject(offlineTask: OfflineTask?): T?
}
