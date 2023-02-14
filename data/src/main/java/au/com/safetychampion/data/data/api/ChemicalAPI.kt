package au.com.safetychampion.data.data.api

import au.com.safetychampion.data.data.local.IStorable
import au.com.safetychampion.data.data.local.ISyncable
import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.models.chemical.ChemicalTask

interface ChemicalAPI {
    class List(
        body: BasePL = BasePL.empty()
    ) : NetworkAPI.Post(
        path = "/chemicals/list",
        body = body
    ),
        IStorable

    class ListCode() :
        NetworkAPI.Get(
            path = "chemicals/list/ghs/codes"
        ),
        IStorable

    class Fetch(
        moduleId: String
    ) : NetworkAPI.Get(
        path = "chemicals/$moduleId/fetch"
    ),
        IStorable

    class Signoff(
        moduleId: String?,
        taskId: String?,
        body: ChemicalTask,
        photos: AttachmentList?
    ) : NetworkAPI.PostMultiParts(
        path = "chemicals/$moduleId/tasks/$taskId/signoff",
        body = body,
        attachment = photos ?: emptyList()
    ),
        ISyncable
}
