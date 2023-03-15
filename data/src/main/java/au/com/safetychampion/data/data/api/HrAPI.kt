package au.com.safetychampion.data.data.api

import au.com.safetychampion.data.data.local.IStorable
import au.com.safetychampion.data.domain.base.BasePL

interface HrAPI {
    class ListLinkedIncidents(
        hrId: String
    ) : NetworkAPI.Get(
        path = "hr/$hrId/listLinkedIncidents"
    )

    class List(
        body: BasePL = BasePL.empty()
    ) : NetworkAPI.Post(
        path = "hr/list",
        body = body
    )

    class Fetch(
        hrId: String
    ) : NetworkAPI.Get(
        path = "hr/$hrId/fetch"
    ),
        IStorable
}