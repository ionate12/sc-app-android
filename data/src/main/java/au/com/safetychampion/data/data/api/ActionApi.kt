package au.com.safetychampion.data.data.api

import au.com.safetychampion.data.data.local.IStorable
import au.com.safetychampion.data.data.local.ISyncable
import au.com.safetychampion.data.domain.Attachment
import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.models.action.ActionTask
import au.com.safetychampion.data.domain.models.action.network.ActionPL

typealias AttachmentList = List<Attachment>

interface ActionApi {
    class New(
        body: ActionPL,
        photos: AttachmentList
    ) : NetworkAPI.PostMultiParts("actions/new", body, photos), ISyncable

    class Edit(
        actionId: String,
        body: ActionPL,
        photos: AttachmentList
    ) : NetworkAPI.PostMultiParts("actions/$actionId/edit", body, photos), ISyncable

    class Fetch(
        actionId: String
    ) : NetworkAPI.Get("actions/$actionId/fetch"), IStorable

    class Task(
        actionId: String
    ) : NetworkAPI.Get("actions/$actionId/task"), IStorable

    class List(
        body: BasePL?
    ) : NetworkAPI.Post("actions/list", body), IStorable

    class SignOff(
        actionId: String,
        body: ActionTask,
        photos: AttachmentList
    ) : NetworkAPI.PostMultiParts("actions/$actionId/task/signoff", body, photos), ISyncable
}
