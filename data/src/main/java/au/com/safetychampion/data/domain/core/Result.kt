package au.com.safetychampion.data.domain.core

import kotlin.reflect.KClass

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
    expectedError: KClass<out SCError>,
    action: suspend (SCError) -> Result<T>?
): Result<T> {
    return if (this is Result.Error && errorOrNull()!!::class == expectedError) {
        action.invoke(err) ?: this
    } else {
        this
    }
}

/**
 * Performs the given action if this is [Result.Success] and data != null,
 * @return true if the action is performed,
 * false otherwise
 */

fun <T> Result<T>.doOnSucceed(action: T.() -> Unit): Boolean {
    dataOrNull()?.let(action) ?: return false
    return true
}

/**
 * Performs the given action if this is [Result.Error]
 * @return true if the action is performed,
 * false otherwise
 */

fun <T> Result<T>.doOnFailure(action: SCError.() -> Unit): Boolean {
    errorOrNull()?.let(action) ?: return false
    return true
}
