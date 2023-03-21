package au.com.safetychampion.data.domain.core

sealed class Result<out R> {
    data class Success<out T>(val data: T, val offline: Boolean = false) : Result<T>()
    data class Error(val err: SCError) : Result<Nothing>()
    object Loading : Result<Nothing>()
}

fun <T> Result<T>.dataOrNull(): T? {
    return (this as? Result.Success)?.data
}

fun Result<*>.errorOrNull(): SCError? {
    return (this as? Result.Error)?.err
}

inline fun <T> Result<T>.flatMapError(action: (SCError) -> Result<T>): Result<T> {
    if (this is Result.Success<*>) return this
    return action.invoke(errorOrNull()!!)
}

inline fun <T, R> Result<T>.flatMap(action: (T) -> Result<R>): Result<R> {
    if (this is Result.Error) return this
    val data = dataOrNull() ?: return Result.Error(SCError.EmptyResult)
    return action.invoke(data)
}

/**
 * Mapping a [Result.Success] to another one by applying the given block on the data value.
 * @param block A high order function that takes the parameter is [Result.Success.data] and returns a value of type [R].
 * @return [Result.Success] with new data is the return value of [block]
 * @throws IllegalStateException*/

inline fun <T, R> Result<T>.map(block: (T) -> R): Result<R> {
    if (this is Result.Error) return this
    this as Result.Success
    return when (val result = block(data)) {
        is SCError, is Result.Error -> error("Not supported yet with SCError and Result.Error")
        else -> Result.Success(result)
    }
}

/**
 * Performs the given action if this is [Result.Success] and data != null,
 * @return self
 */

inline fun <T> Result<T>.doOnSuccess(action: (T) -> Unit): Result<T> {
    dataOrNull()?.let {
        action.invoke(it)
    }
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
