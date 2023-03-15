package au.com.safetychampion.data.domain.core

import au.com.safetychampion.data.visitor.domain.models.Destination

fun errorOf(message: String) = Result.Error(
    SCError.Failure(
        listOf(message)
    )
)

sealed class SCError(val code: String, val errDescription: String) {
    override fun toString(): String {
        return "An error has occurred: $code \n $errDescription"
    }

    class Failure(detailsMsg: List<String> = emptyList()) : SCError(
        code = "backend_response_error",
        errDescription = "You can not perform this action because: $detailsMsg"
    ) {
        constructor(message: String) : this(listOf(message))
    }

    object EmptyResult : SCError(
        code = "success_no_result",
        errDescription = "Succeed but empty result."
    )

    object LoginTokenExpired : SCError(
        code = "authorization_token_expired",
        errDescription = "Your LOGIN SESSION has expired. Re-login is required"
    )

    object NoNetwork : SCError(
        code = "no_internet_connection",
        errDescription = "No Internet. Please check your internet connection"
    )

    class FailedInCombineFetchAndTask(
        scError: List<SCError>
    ) : SCError(
        code = "can_not_combine_fetch_and_task",
        errDescription = "can_not_combine_fetch_and_task"
    )

    class SyncableStored(val syncableId: String) : SCError(
        code = "syncable_stored",
        errDescription = "Due to offline network, this task has been stored as a Offline Task, and it will be synced with Safety Champion System when going back online."
    )

    /**
     *  Represents an invalid QR code request.
     *  @param [des] the [Destination] would like to go, this is an optional*/
    class InvalidQRCodeRequest(
        val des: Destination? = null
    ) : SCError(
        "invalid_qr",
        "Invalid Token"
    )
}
