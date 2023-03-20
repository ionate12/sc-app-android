package au.com.safetychampion.data.data.api

import au.com.safetychampion.data.data.local.IStorable
import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.models.inspections.payload.InspectionNewChildPL
import au.com.safetychampion.data.domain.models.inspections.payload.InspectionTaskPL
import au.com.safetychampion.data.domain.models.inspections.payload.InspectionTaskStartPL

interface InspectionApi {
    class AvailableList(
        body: BasePL
    ) : NetworkAPI.Post("/inspections/list/available", body), IStorable
    class NewChild(
        inspectionId: String,
        body: InspectionNewChildPL
    ) : NetworkAPI.PostMultiParts("/inspections/$inspectionId/newchild", body)
    class TaskStart(
        inspectionId: String,
        taskId: String,
        body: InspectionTaskStartPL
    ) : NetworkAPI.PostMultiParts("/inspections/$inspectionId/tasks/$taskId/start", body)
    class Signoff(
        inspectionId: String,
        taskId: String,
        body: InspectionTaskPL
    ) : NetworkAPI.PostMultiParts("/inspections/$inspectionId/tasks/$taskId/signoff", body)
    class FetchTask(
        inspectionId: String,
        taskId: String
    ) : NetworkAPI.Get("/inspections/$inspectionId/tasks/$taskId"), IStorable
    class Fetch(
        inspectionId: String
    ) : NetworkAPI.Get("/inspections/$inspectionId/fetch"), IStorable
    class Allocation(
        inspectionId: String
    ) : NetworkAPI.Get("/inspections/$inspectionId/allocate")
    class LinkedDocs(
        inspectionId: String,
        taskId: String
    ) : NetworkAPI.Get("/inspections/$inspectionId/tasks/$taskId/links/vdocs"), IStorable
}
