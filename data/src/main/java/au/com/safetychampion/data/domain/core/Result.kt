package au.com.safetychampion.data.domain.core

sealed class Result<out R> {
    data class Success<out T>(val data: T?) : Result<T>()
    data class Error(val err: SCError) : Result<Nothing>()
}

fun <T> Result<T>.getData(): T? {
    return (this as? Result.Success)?.data
}

fun Result<*>.getError(): SCError? {
    return (this as? Result.Error)?.err
}

// fun <T, M> Result<T>.flatMap(function: (T?) -> M?): Result<M> {
//    val data = function(getData())
//    return when {
//        data != null -> Result.Success(data)
//        this is Result.Success -> Result.Error(SCError.EmptyResult)
//        else -> this as Result.Error
//    }
// }

/**
 * Performs the given action if this is [Result.Success] and data != null,
 * @return true if this is [Result.Success] and data != null
 * false otherwise
 */

fun <T> Result<T>.doOnSucceed(action: T.() -> Unit): Boolean {
    getData()?.let(action) ?: return false
    return true
}

/**
 * Performs the given action if this is [Result.Error]
 * @return true if this is [Result.Error]
 * false otherwise
 */

fun <T> Result<T>.doOnFailure(action: SCError.() -> Unit): Boolean {
    if (this is Result.Error) {
        action(err)
        return true
    }
    return false
}
