package au.com.safetychampion.data.data.api

import au.com.safetychampion.data.data.local.IStorable
import au.com.safetychampion.data.domain.models.safetyplan.SafetyPlanTask

interface SafetyPlanAPI {
    class Fetch(safeId: String) :
        NetworkAPI.Get(
            path = "safetyplans/$safeId/fetch"
        ),
        IStorable

    class FetchTask(
        safeId: String,
        taskId: String
    ) : NetworkAPI.Get(
        path = "safetyplans/$safeId/tasks/$taskId"
    ),
        IStorable
    class Signoff(
        safeId: String,
        taskId: String,
        payload: SafetyPlanTask
    ) : NetworkAPI.PostMultiParts(
        path = "safetyplans/$safeId/tasks/$taskId/signoff",
        body = payload
    )
}
