package au.com.safetychampion.data.data.inspection

import au.com.safetychampion.data.data.BaseRepository
import au.com.safetychampion.data.data.api.InspectionApi
import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.models.document.Document
import au.com.safetychampion.data.domain.models.inspections.Inspection
import au.com.safetychampion.data.domain.models.inspections.InspectionTask
import au.com.safetychampion.data.domain.models.inspections.payload.InspectionNewChildPL
import au.com.safetychampion.data.domain.models.inspections.payload.InspectionTaskPL
import au.com.safetychampion.data.domain.models.inspections.payload.InspectionTaskStartPL

interface IInspectionRepository {
    suspend fun getAvailableList(): Result<List<Inspection>>
    suspend fun fetch(inspectionId: String): Result<Inspection>
    suspend fun fetchTask(inspectionId: String, taskId: String): Result<InspectionTask>
    suspend fun linkedDocs(inspectionId: String, taskId: String): Result<List<Document>>
    suspend fun newChild(parentInspId: String, body: InspectionNewChildPL): Result<Inspection>
    suspend fun taskStart(
        inspId: String,
        taskId: String,
        body: InspectionTaskStartPL
    ): Result<InspectionTask>
    suspend fun signoff(
        inspId: String,
        taskId: String,
        body: InspectionTaskPL
    ): Result<InspectionTask>
}

class InspectionRepository : BaseRepository(), IInspectionRepository {
    override suspend fun getAvailableList(): Result<List<Inspection>> {
        return InspectionApi.AvailableList(BasePL.empty()).call()
    }

    override suspend fun fetch(inspectionId: String): Result<Inspection> {
        return InspectionApi.Fetch(inspectionId).call()
    }

    override suspend fun fetchTask(inspectionId: String, taskId: String): Result<InspectionTask> {
        return InspectionApi.FetchTask(inspectionId, taskId).call()
    }

    override suspend fun linkedDocs(inspectionId: String, taskId: String): Result<List<Document>> {
        return InspectionApi.LinkedDocs(inspectionId, taskId).call()
    }

    override suspend fun newChild(
        parentInspId: String,
        body: InspectionNewChildPL
    ): Result<Inspection> {
        return InspectionApi.NewChild(parentInspId, body).call()
    }

    override suspend fun taskStart(
        inspId: String,
        taskId: String,
        body: InspectionTaskStartPL
    ): Result<InspectionTask> = InspectionApi.TaskStart(inspId, taskId, body).call()

    override suspend fun signoff(
        inspId: String,
        taskId: String,
        body: InspectionTaskPL
    ): Result<InspectionTask> = InspectionApi.Signoff(inspId, taskId, body).call()
}
