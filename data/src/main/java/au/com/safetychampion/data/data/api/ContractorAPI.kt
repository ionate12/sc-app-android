package au.com.safetychampion.data.data.api

import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.models.contractor.ContractorLinkedTasks

interface ContractorAPI {
    class List(
        body: BasePL?
    ) : NetworkAPI.Post(
        path = "contractors/list",
        body = body
    )

    class Fetch(
        id: String
    ) : NetworkAPI.Get(
        path = "contractors/$id/fetch"
    )

    class LinkedTasks(
        body: ContractorLinkedTasks
    ) : NetworkAPI.Post(
        path = "contractors/list/linkedTasks",
        body = body
    )
}
