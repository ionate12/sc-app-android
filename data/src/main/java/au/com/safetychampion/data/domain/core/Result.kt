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

suspend fun <T : Any> Result<T>.flatMapError(
    action: suspend (SCError) -> Result<T>?
): Result<T> {
    if (this is Result.Success<*>) return this
    return action.invoke(errorOrNull()!!) ?: this
}
