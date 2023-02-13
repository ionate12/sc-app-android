package au.com.safetychampion.data.data.api

import au.com.safetychampion.data.data.local.IStorable
import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.models.crisk.CriskArchivePayload
import au.com.safetychampion.data.domain.models.crisk.CriskTaskPL

interface CriskAPI {
    class List(
        body: BasePL = BasePL.empty()
    ) : NetworkAPI.Post(
        path = "crisks/list",
        body = body
    ),
        IStorable

    class HrLookUp(
        body: BasePL = BasePL.empty()
    ) : NetworkAPI.Post(
        path = "crisks/hrlookup",
        body = body
    )

    class ContractorLookUp(
        body: BasePL = BasePL.empty()
    ) : NetworkAPI.Post(
        path = "crisks/contractorlookup",
        body = body
    )

    class Fetch(
        criskId: String
    ) : NetworkAPI.Get(
        path = "/crisks/$criskId/fetch"
    ),
        IStorable

    class Tasks(
        criskId: String,
        taskId: String = ""
    ) : NetworkAPI.Get(
        path = "/crisks/$criskId/tasks/$taskId"
    )

    class LinkedActions(
        criskId: String
    ) : NetworkAPI.Get(
        path = "/crisks/$criskId/links/actions"
    )

    class Signoff(
        criskId: String,
        taskId: String,
        body: CriskTaskPL
    ) : NetworkAPI.PostMultiParts(
        path = "/crisks/$criskId/tasks/$taskId/signoff",
        body = body
    )

    class Archive(
        criskId: String,
        body: CriskArchivePayload
    ) : NetworkAPI.Post(
        path = "crisks/$criskId/archive",
        body = body
    )
}
