package au.com.safetychampion.data.data.api

import au.com.safetychampion.data.data.local.IStorable
import au.com.safetychampion.data.data.local.ISyncable
import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.models.incidents.IncidentNewPL
import au.com.safetychampion.data.domain.models.incidents.IncidentTaskPL

interface IncidentAPI {

    class List :
        NetworkAPI.Post(
            path = "incidents/list",
            body = BasePL.empty()
        ),
        IStorable

    class New(
        body: IncidentNewPL
    ) : NetworkAPI.PostMultiParts(
        path = "/incidents/new",
        body = body
    ),
        ISyncable

    class Fetch(
        taskID: String
    ) : NetworkAPI.Get(
        path = "incidents/$taskID/fetch"
    ),
        IStorable

    class Task(taskID: String) :
        NetworkAPI.Get(
            path = "incidents/$taskID/task"
        ),
        IStorable

    class ConfigLocations :
        NetworkAPI.Get(
            path = "/incidents/list/configured-locations"
        ),
        IStorable

    class Signoff(
        incidentID: String,
        payload: IncidentTaskPL
    ) : NetworkAPI.PostMultiParts(
        path = "incidents/$incidentID/task/signoff",
        body = payload
    )

    class HRLookup :
        NetworkAPI.Post(
            path = "incidents/hrlookup",
            body = BasePL.empty()
        ),
        IStorable
}
