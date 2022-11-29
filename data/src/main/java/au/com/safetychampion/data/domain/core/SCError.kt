package au.com.safetychampion.data.domain.core

import au.com.safetychampion.data.network.APIResponse

sealed class SCError(val code: String, val errDescription: String) {
    object Unknown : SCError("unknown_error", "Unknown error has occurred.")
    object EmptyResult : SCError("success_no_result", "Succeed but empty result.")

    companion object {
        fun fromApiError(apiError: APIResponse.APIError): SCError {
            // TODO: Map APIError to SC Error
            return SCError.Unknown
        }
    }
}
