package au.com.safetychampion.data.domain.extensions

import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.core.SCError
import au.com.safetychampion.data.network.APIResponse

internal interface BaseRepositoryExtensions {

    /** Gets the result of [APIResponse] as [T] (wrapped in [keyItem] object).
     * @return [Result.Success], [Result.Error]
     * @see asT
     */

    fun <T> APIResponse.get(keyItem: String, tClass: Class<T>): Result<T> {
        if (success) {
            if (result == null) {
                return Result.Error(SCError.EmptyResult)
            }
            return result
                .get(keyItem)
                .asT(tClass)
        } else {
            return Result.Error(handleError(error))
        }
    }

    /** Gets the result of [APIResponse] as List<`T`> (wrapped in "items" object).
     * @return [Result.Success], [Result.Error]
     * @see asListT
     */

    fun <T> APIResponse.getAsList(tClass: Class<T>): Result<List<T>> {
        if (success) {
            if (result == null) {
                return Result.Error(SCError.EmptyResult)
            }
            return result
                .get("items")
                .asListT(tClass)
        } else {
            return Result.Error(
                handleError(error)
            )
        }
    }

    private fun handleError(err: APIResponse.APIError?): SCError {
        return when {
            err?.code?.contains("authorization_token_expired") == true -> SCError.LoginTokenExpired()
            else -> SCError.Failure(err?.message ?: listOf("Unknown Reason"))
        }
    }
}
