package au.com.safetychampion.scmobile.modules.inspection.model

import au.com.safetychampion.data.domain.models.inspections.BaseRequest
import au.com.safetychampion.data.domain.uncategory.Constants

data class InspectionNewChildRequest(
    override var params: Params,
    override var requestPayload: Payload?
) : BaseRequest<InspectionNewChildRequest.Params, InspectionNewChildRequest.Payload> {
    data class Params(val parentInspectionId: String)
    data class Payload(val dateDue: String, val notes: String?)
}

data class InspectionStartRequest(
    override var params: Params,
    override var requestPayload: Payload?
) : BaseRequest<InspectionStartRequest.Params, InspectionStartRequest.Payload> {
    data class Params(
        val inspectionId: String,
        val taskId: String
    )
    data class Payload(val dateCommenced: String?, val tz: String = Constants.tz)
}
