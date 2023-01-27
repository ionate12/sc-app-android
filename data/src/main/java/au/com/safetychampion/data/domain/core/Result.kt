package au.com.safetychampion.data.domain.core

sealed class Result<out R> {
    data class Success<out T>(val data: T?) : Result<T>()
    data class Error(val err: SCError) : Result<Nothing>()
    object Loading : Result<Nothing>()
}

fun <T> Result<T>.dataOrNull(): T? {
    return (this as? Result.Success)?.data
}

fun Result<*>.errorOrNull(): SCError? {
    return (this as? Result.Error)?.err
}

suspend fun <T> Result<T>.flatMapError(
    action: suspend (SCError) -> Result<T>?
): Result<T> {
    if (this is Result.Success) return this
    return action.invoke(errorOrNull()!!) ?: this
}

suspend fun <T> Result<T>.flatMapSuccess(
    action: (T?) -> Result<T>?
): Result<T> {
    if (this is Result.Error) return this
    return action.invoke(dataOrNull()) ?: this
}

/**
 * Performs the given action if this is [Result.Success] and data != null,
 * @return self
 */

fun <T> Result<T>.doOnSucceed(action: (T) -> Unit): Result<T> {
    dataOrNull()?.let(action)
    return this
}

/**
 * Performs the given action if this is [Result.Error]
 * @return self
 */

fun <T> Result<T>.doOnFailure(action: SCError.() -> Unit): Result<T> {
    errorOrNull()?.let(action)
    return this
}
