package au.com.safetychampion.data.domain.core

sealed class Result<out R> {
    data class Success<out T>(val data: T?, val offline: Boolean = false) : Result<T>()
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
