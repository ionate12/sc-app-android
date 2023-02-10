package au.com.safetychampion.data.data.api

import au.com.safetychampion.data.data.local.IStorable
import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.models.contractor.ContractorLinkedTaskPL

interface ContractorAPI {
    class List(
        body: BasePL?
    ) : NetworkAPI.Post(
        path = "contractors/list",
        body
    ),
        IStorable

    class Fetch(
        id: String
    ) : NetworkAPI.Get(
        path = "contractors/$id/fetch"
    ),
        IStorable

    class LinkedTasks(
        body: ContractorLinkedTaskPL
    ) : NetworkAPI.Post(
        path = "contractors/list/linkedTasks",
        body
    )
}
