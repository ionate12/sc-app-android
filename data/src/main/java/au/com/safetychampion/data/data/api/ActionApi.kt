package au.com.safetychampion.data.data.api

import au.com.safetychampion.data.data.local.IStorable
import au.com.safetychampion.data.data.local.ISyncable
import au.com.safetychampion.data.domain.Attachment
import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.models.action.ActionTaskPL
import au.com.safetychampion.data.domain.models.action.network.ActionPL

typealias AttachmentList = List<Attachment>

internal interface ActionApi {
    // Only use for actionLinks
    class NewOnline(
        body: ActionPL
    ) : NetworkAPI.PostMultiParts("actions/new", body)
    class New(
        body: ActionPL
    ) : NetworkAPI.PostMultiParts("actions/new", body), ISyncable

    class Edit(
        actionId: String,
        body: ActionPL
    ) : NetworkAPI.PostMultiParts("actions/$actionId/edit", body), ISyncable

    class Fetch(
        actionId: String
    ) : NetworkAPI.Get("actions/$actionId/fetch"), IStorable

    class Task(
        actionId: String
    ) : NetworkAPI.Get("actions/$actionId/task"), IStorable

    class List(
        body: BasePL?
    ) : NetworkAPI.Post("actions/list", body), IStorable

    class Signoff(
        actionId: String,
        body: ActionTaskPL
    ) : NetworkAPI.PostMultiParts("actions/$actionId/task/signoff", body), ISyncable
}
