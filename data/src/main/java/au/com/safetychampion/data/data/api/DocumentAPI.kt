package au.com.safetychampion.data.data.api

import au.com.safetychampion.data.data.local.IStorable
import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.models.document.DocumentTask

interface DocumentAPI {
    class List() :
        NetworkAPI.Post(
            path = "/vdocs/list",
            body = BasePL.empty()
        ),
        IStorable

    class Fetch(moduleId: String) : NetworkAPI.Get(path = "vdocs/$moduleId/fetch"), IStorable

    class CopySource(moduleId: String) : NetworkAPI.Get(path = "vdocs/$moduleId/fetch/copysource"), IStorable

    class Signoff(
        moduleId: String,
        taskId: String,
        body: DocumentTask
    ) : NetworkAPI.PostMultiParts(
        path = "vdocs/$moduleId/tasks/$taskId/signoff",
        body = body
    )
}
