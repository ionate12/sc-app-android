package au.com.safetychampion.data.domain.models.action.network

import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.models.action.ActionTask

data class ActionSignOffPL(
    var _id: String?,
    val moduleId: String?,
    var body: ActionPL? = null,
    val task: ActionTask
) : BasePL()
