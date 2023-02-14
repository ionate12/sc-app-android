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

suspend fun <T> Result<T>.flatMapError(
    action: suspend (SCError) -> Result<T>?
): Result<T> {
    if (this is Result.Success<*>) return this
    return action.invoke(errorOrNull()!!) ?: this
}

suspend fun <T, R> Result<T>.flatMap(
    action: suspend (T) -> Result<R>
): Result<R> {
    if (this is Result.Error) return this
    val data = dataOrNull() ?: return Result.Error(SCError.EmptyResult)
    return action.invoke(data)
}

suspend fun <T, R> Result<T>.map(block: suspend (T) -> R): Result<R> {
    return when (this) {
        is Result.Error -> return this
        is Result.Success -> {
            return data?.let {
                Result.Success(block(it))
            } ?: Result.Error(SCError.EmptyResult)
        }
        else -> TODO("TO BE REMOVED")
    }
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
