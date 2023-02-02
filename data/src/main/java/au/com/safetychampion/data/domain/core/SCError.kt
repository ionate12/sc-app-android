package au.com.safetychampion.data.domain.core

sealed class SCError(val code: String, val errDescription: String) {
    override fun toString(): String {
        return "An error has occurred: $code \n $errDescription"
    }

    class Failure(detailsMsg: List<String> = emptyList()) : SCError(
        code = "backend_response_error",
        errDescription = "You can not perform this action because: $detailsMsg"
    )
    object EmptyResult : SCError(
        code = "success_no_result",
        errDescription = "Succeed but empty result."
    )

    object Unknown : SCError(
        code = "unknown_error",
        errDescription = "Unknown error has occurred."
    )

    class JsonSyntaxException(detailsMsg: String) : SCError(
        code = detailsMsg,
        errDescription = "Data not available"
    )

    object LoginTokenExpired : SCError(
        code = "authorization_token_expired",
        errDescription = "Your LOGIN SESSION has expired. Re-login is required"
    )

    object NoNetwork : SCError(
        code = "no_internet_connection",
        errDescription = "No Internet. Please check your internet connection"
    )

    class SignOffFailed(
        moduleName: String,
        title: String,
        details: String
    ) : SCError(
        code = "D",
        errDescription = details
    )

    object AlreadySignedOff : SCError(
        code = "closed_or_signed_off",
        errDescription = "This task is already closed or signed-off."
    )

    class SyncableStored(val syncableId: String) : SCError(
        code = "syncable_stored",
        errDescription = "Due to offline network, this task has been stored as a Offline Task, and it will be synced with Safety Champion System when going back online."
    )
}
