package au.com.safetychampion.data.domain.models.inspections

/**
 * Base Request
 * Params: Parameters of request.
 * Payload: Payload of the request, Any? = null if not required
 */
interface BaseRequest<Params, Payload : Any?> {
    var params: Params
    var requestPayload: Payload?
}
