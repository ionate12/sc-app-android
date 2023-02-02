package au.com.safetychampion.data.data.api

import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.models.chemical.ChemicalTask
import au.com.safetychampion.data.domain.usecase.ISignoffGeneral
import au.com.safetychampion.data.domain.usecase.chemical.ChemicalSignoffParam

interface ChemicalAPI : ISignoffGeneral<ChemicalSignoffParam> {
    class List(
        body: BasePL = BasePL.empty()
    ) : NetworkAPI.Post(
        path = "/chemicals/list",
        body = body
    )

    class ListCode() : NetworkAPI.Get(
        path = "chemicals/list/ghs/codes"
    )

    class Fetch(
        moduleId: String
    ) : NetworkAPI.Get(
        path = "chemicals/$moduleId/fetch"
    )

    class Signoff(
        moduleId: String?,
        taskId: String?,
        body: ChemicalTask,
        photos: AttachmentList?
    ) : NetworkAPI.PostMultiParts(
        path = "chemicals/$moduleId/tasks/$taskId/signoff",
        body = body,
        attachment = photos ?: emptyList()
    )
}
