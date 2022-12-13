package au.com.safetychampion.data.data

import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.core.SCError
import au.com.safetychampion.data.domain.extensions.asListT
import au.com.safetychampion.data.domain.extensions.asT
import au.com.safetychampion.data.network.APIResponse

internal interface BaseRepositoryExtensions {

    /** Gets the result of [APIResponse] as object T (wrapped in "item" object).
     * @return [Result.Success], [Result.Error]
     * @see asT
     */

    fun <T> APIResponse.get(tClass: Class<T>): Result<T> {
        if (success) {
            if (result == null) {
                return Result.Error(SCError.EmptyResult)
            }
            return result
                .get("item")
                .asT(tClass)
        } else {
            return Result.Error(SCError.Failure(error?.message ?: listOf("Unknown Reason")))
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
            return Result.Error(SCError.Failure(error?.message ?: listOf("Unknown Reason")))
        }
    }
}
