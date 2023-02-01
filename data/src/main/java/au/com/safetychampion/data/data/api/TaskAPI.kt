package au.com.safetychampion.data.data.api

import au.com.safetychampion.data.domain.models.action.network.ActiveTaskPL
import au.com.safetychampion.data.domain.models.action.network.AssignTaskStatusManyPL
import au.com.safetychampion.data.domain.models.action.network.AssignTaskStatusPL

interface TaskAPI {
    class Active(
        body: ActiveTaskPL
    ) : NetworkAPI.Post(
        path = "tasks/list/active",
        body = body
    )
    class AssignTaskStatus(
        body: AssignTaskStatusPL
    ) : NetworkAPI.Post(
        path = "tasks/assign/status",
        body = body
    )

    class AssignTaskStatusMany(
        body: AssignTaskStatusManyPL
    ) : NetworkAPI.Post(
        path = "tasks/assign/status",
        body = body
    )

    class AssignTask(
        body: AssignTaskStatusPL?
    ) : NetworkAPI.Post(
        path = "tasks/assign",
        body = body
    )

    class UnAssignTask(
        body: AssignTaskStatusPL
    ) : NetworkAPI.Post(
        path = "tasks/unassign",
        body = body
    )
}
